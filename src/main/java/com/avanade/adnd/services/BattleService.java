package com.avanade.adnd.services;

import com.avanade.adnd.exceptions.*;
import com.avanade.adnd.model.Battle;
import com.avanade.adnd.model.BattleLog;
import com.avanade.adnd.model.Character;
import com.avanade.adnd.model.PlayerCharacter;
import com.avanade.adnd.model.enums.BattleStatus;
import com.avanade.adnd.model.enums.CharacterType;
import com.avanade.adnd.model.enums.Initiative;
import com.avanade.adnd.model.enums.TurnStatus;
import com.avanade.adnd.payloads.CreateBattleLogRequest;
import com.avanade.adnd.payloads.CreateBattleRequest;
import com.avanade.adnd.repository.BattleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BattleService {

    private final BattleRepository battleRepository;

    private final BattleLogService battleLogService;

    private final PlayerCharacterService playerCharacterService;

    private final CharacterService characterService;

    public BattleService(BattleRepository battleRepository, BattleLogService battleLogService, PlayerCharacterService playerCharacterService, CharacterService characterService) {
        this.battleRepository = battleRepository;
        this.battleLogService = battleLogService;
        this.playerCharacterService = playerCharacterService;
        this.characterService = characterService;
    }

    public Battle createBattle(CreateBattleRequest createBattleRequest) throws Exception {
        Battle newBattle = new Battle();
        PlayerCharacter playerCharacter = this.playerCharacterService.findPlayerCharacterById(createBattleRequest.getPlayerCharacterId());
        Character enemy = this.characterService.findCharacterById(createBattleRequest.getEnemyId());
        if(enemy.getType() != CharacterType.MONSTER) {
            throw new BattleCreationException("The enemy must be a monster");
        }

        newBattle.setPlayerCharacter(playerCharacter);
        newBattle.setEnemy(enemy);
        newBattle.setStatus(BattleStatus.CREATED);
        newBattle.setInitiative(Initiative.WAITING_ROLL);
        return battleRepository.save(newBattle);
    }

    public List<Battle> findAllBattles() {
        return battleRepository.findAll();
    }

    public List<Battle> findAllBattlesForPlayerCharacter(UUID playerId) {
        return battleRepository.findAllByPlayerCharacterId(playerId);
    }

    public Battle findBattleById(Long battleId) throws NotFoundException {
        return battleRepository.findById(battleId)
                .orElseThrow(() -> new NotFoundException("Battle with id ${battleId} not found"));
    }

    public Battle updateBattleWithId(Long battleId, Battle battle) throws Exception {
        Battle battleToUpdate = battleRepository.findById(battleId)
                .orElseThrow(() -> new Exception("Battle with id ${id} not found"));
        battleToUpdate.setPlayerCharacter(battle.getPlayerCharacter());
        battleToUpdate.setEnemy(battle.getEnemy());
        battleToUpdate.setStatus(battle.getStatus());
        battleToUpdate.setInitiative(battle.getInitiative());
        return battleRepository.save(battleToUpdate);
    }

    public Battle updateBattle(Battle battle) {
        return battleRepository.save(battle);
    }

    public void deleteBattleById(Long battleId) throws NotFoundException {
        Battle battle = battleRepository.findById(battleId)
                .orElseThrow(() -> new NotFoundException("Battle with id ${battleId} not found"));
        battleRepository.deleteById(battle.getId());
    }

    public String rollInitiative(Long battleId) throws Exception {
        Battle battle = this.findBattleById(battleId);
        if(battle.getInitiative() != Initiative.WAITING_ROLL) {
            throw new InitiativeAlreadyDecidedException();
        }
        int playerInitiativeRoll = 0;
        int enemyInitiativeRoll = 0;
        while (playerInitiativeRoll == enemyInitiativeRoll) {
            playerInitiativeRoll = DiceService.rollDices(1, 20).get(0);
            enemyInitiativeRoll = DiceService.rollDices(1, 20).get(0);
        }

        CreateBattleLogRequest createBattleLogRequest = new CreateBattleLogRequest();
        if(playerInitiativeRoll > enemyInitiativeRoll) {
            battle.setInitiative(Initiative.PLAYER_CHARACTER);
            createBattleLogRequest.setStatus(TurnStatus.AWAITING_PLAYER_ATTACK);
        } else {
            battle.setInitiative(Initiative.ENEMY_CHARACTER);
            createBattleLogRequest.setStatus(TurnStatus.AWAITING_PLAYER_DEFENSE);
        }
        this.updateBattle(battle);
        createBattleLogRequest.setBattleId(battleId);
        createBattleLogRequest.setTurn(1);
        createBattleLogRequest.setPlayerCharacterHealthStartOfTurn(battle.getPlayerHitPoints());
        createBattleLogRequest.setNonPlayerCharacterHealthStartOfTurn(battle.getEnemyHitPoints());
        this.battleLogService.createBattleLogWithRequest(createBattleLogRequest, battle);

        return "Player Initiative Roll: " + playerInitiativeRoll + "\nEnemy Initiative Roll: " + enemyInitiativeRoll + "\nInitiative: " + battle.getInitiative();
    }

    public String makePlayerCharacterAttack(Long battleId) throws Exception {
        Battle battle = this.findBattleById(battleId);
        this.checkIfPlayerCanMakeAction(battle, TurnStatus.AWAITING_PLAYER_ATTACK);
        Integer attackDice = DiceService.rollDices(1, 12).get(0);

        Integer playerCharacterAttack = attackDice + battle.getPlayerStrength() + battle.getPlayerAgility();

        Integer enemyDefenseDice = DiceService.rollDices(1, 12).get(0);
        Integer enemyDefenseValue = enemyDefenseDice + battle.getEnemyDefense() + battle.getEnemyAgility();

        BattleLog lastLog = this.getLastBattleLogByBattleId(battleId);
        lastLog.setPlayerCharacterAttackDice(attackDice);
        lastLog.setNonPlayerCharacterDefenseDice(enemyDefenseDice);

        String responseMessage;
        if (playerCharacterAttack > enemyDefenseValue) {
            lastLog.setStatus(TurnStatus.AWAITING_PLAYER_DAMAGE);
            responseMessage = "You hit the enemy!, make a damage roll \nAttack Dice: " + attackDice + " Total Attack: " + playerCharacterAttack +
                    "\nEnemy Defense Dice: " + enemyDefenseDice + " Enemy Total Defense: " + enemyDefenseValue;
        } else {
            lastLog.setNonPlayerCharacterHealthEndOfTurn(lastLog.getNonPlayerCharacterHealthStartOfTurn());
            if(lastLog.getNonPlayerCharacterAttackDice() != null) {
                lastLog.setStatus(TurnStatus.FINISHED);
                this.createNewTurn(battleId);
            } else {
                lastLog.setStatus(TurnStatus.AWAITING_PLAYER_DEFENSE);
            }
            responseMessage = "You miss the attack!\nAttack Dice: " + attackDice + "\n Enemy Defense: " + enemyDefenseDice;
        }
        this.battleLogService.updateBattleLog(lastLog);
        return responseMessage;
    }

    public String makePlayerCharacterDefense(Long battleId) throws Exception {
        Battle battle = this.findBattleById(battleId);
        this.checkIfPlayerCanMakeAction(battle, TurnStatus.AWAITING_PLAYER_DEFENSE);
        Integer enemyAtackDice = DiceService.rollDices(1, 12).get(0);
        Integer playerCharacterDefenseDice = DiceService.rollDices(1, 12).get(0);
        Integer enemyAttack = enemyAtackDice + battle.getEnemyStrength() + battle.getEnemyAgility();
        Integer playerCharacterDefense = playerCharacterDefenseDice + battle.getPlayerDefense() + battle.getPlayerAgility();

        BattleLog lastLog = this.getLastBattleLogByBattleId(battleId);
        lastLog.setNonPlayerCharacterAttackDice(enemyAtackDice);
        lastLog.setPlayerCharacterDefenseDice(playerCharacterDefenseDice);
        this.battleLogService.updateBattleLog(lastLog);
        String responseMessage;
        ArrayList<String> npcDamageResponse;
        if (enemyAttack > playerCharacterDefense) {
            responseMessage = "The enemy hit you!\nEnemy Attack Dice: " + enemyAtackDice + "\nPlayer Character Defense Dice: " + playerCharacterDefenseDice;
            npcDamageResponse = this.calculateNonPlayerCharacterDamage(battleId);
            responseMessage += "\n" + npcDamageResponse.get(0);
            if(npcDamageResponse.get(1).equals(PlayerCharacterStatus.DEAD.toString())) {
                return responseMessage;
            }
        } else {
            lastLog.setPlayerCharacterHealthEndOfTurn(lastLog.getPlayerCharacterHealthStartOfTurn());
            responseMessage = "The enemy miss the attack!\nEnemy Attack Dice: " + enemyAtackDice + "\nPlayer Character Defense Dice: " + playerCharacterDefenseDice;
        }
        BattleLog updatedLastLog = this.getLastBattleLogByBattleId(battleId);
        if(updatedLastLog.getPlayerCharacterAttackDice() != null) {
            updatedLastLog.setStatus(TurnStatus.FINISHED);
            this.createNewTurn(battleId);
        } else {
            updatedLastLog.setStatus(TurnStatus.AWAITING_PLAYER_ATTACK);
        }

        this.battleLogService.updateBattleLog(updatedLastLog);
        return responseMessage;
    }

    private void checkIfPlayerCanMakeAction(Battle battle, TurnStatus turnStatus) throws Exception {
        if(battle.getStatus() == BattleStatus.PLAYER_CHARACTER_LOST || battle.getStatus() == BattleStatus.PLAYER_CHARACTER_WON) {
            throw new BattleException("Battle is finished");
        }
        BattleLog lastLog = this.getLastBattleLogByBattleId(battle.getId());
        if(lastLog.getStatus() == TurnStatus.FINISHED) {
            throw new BattleException("Turn is already finished");
        }
        if(lastLog.getStatus() != turnStatus) {
            throw new BattleException("Player can't " + turnStatus.getDescription() + " now, try other action");
        }
    }

    public BattleLog getLastBattleLogByBattleId(Long battleId) throws Exception {
        return this.battleLogService.findLastBattleLogByBattleId(battleId);
    }

    private BattleLog createNewTurn(Long battleId) throws Exception {
        BattleLog lastTurn = this.getLastBattleLogByBattleId(battleId);
        Battle battle = this.findBattleById(battleId);
        CreateBattleLogRequest createBattleLogRequest = new CreateBattleLogRequest();
        if(battle.getInitiative() == Initiative.PLAYER_CHARACTER) {
            createBattleLogRequest.setStatus(TurnStatus.AWAITING_PLAYER_ATTACK);
        } else {
            createBattleLogRequest.setStatus(TurnStatus.AWAITING_PLAYER_DEFENSE);
        }
        createBattleLogRequest.setBattleId(battleId);
        createBattleLogRequest.setTurn(lastTurn.getTurn() + 1);
        createBattleLogRequest.setPlayerCharacterHealthStartOfTurn(lastTurn.getPlayerCharacterHealthEndOfTurn());
        createBattleLogRequest.setNonPlayerCharacterHealthStartOfTurn(lastTurn.getNonPlayerCharacterHealthEndOfTurn());
        return this.battleLogService.createBattleLogWithRequest(createBattleLogRequest, battle);
    }

    public String calculateAttackDamage(Long battleId) throws Exception {
        Battle battle = this.findBattleById(battleId);
        this.checkIfPlayerCanMakeAction(battle, TurnStatus.AWAITING_PLAYER_DAMAGE);
        BattleLog lastLog = this.getLastBattleLogByBattleId(battleId);
        ArrayList<Integer> attackDices = DiceService.rollDices(battle.getPlayerDiceAmount(), battle.getPlayerDiceSides());
        Integer playerAttack = attackDices.stream().mapToInt(Integer::intValue).sum() + battle.getPlayerStrength();
        lastLog.setPlayerCharacterAttackDamage(playerAttack);

        Integer enemyCharacterHealth = lastLog.getNonPlayerCharacterHealthStartOfTurn() - playerAttack;
        lastLog.setNonPlayerCharacterHealthEndOfTurn(enemyCharacterHealth);
        String responseMessage;
        if(enemyCharacterHealth <= 0) {
            battle.setStatus(BattleStatus.PLAYER_CHARACTER_WON);
            lastLog.setStatus(TurnStatus.FINISHED);
            this.updateBattle(battle);
            responseMessage = "You cause " + playerAttack + " damage to the enemy, reducing the enemy HP from " + lastLog.getNonPlayerCharacterHealthStartOfTurn() + " to " + enemyCharacterHealth + "\nYou won the battle!";
        } else {
            responseMessage = "You cause " + playerAttack + " damage to the enemy, reducing the enemy HP from " + lastLog.getNonPlayerCharacterHealthStartOfTurn() + " to " + enemyCharacterHealth + "\nEnemy HP: " + enemyCharacterHealth;
            if(lastLog.getNonPlayerCharacterAttackDice() != null) {
                lastLog.setStatus(TurnStatus.FINISHED);
                this.createNewTurn(battleId);
            } else {
                lastLog.setStatus(TurnStatus.AWAITING_PLAYER_DEFENSE);
            }
        }

        this.battleLogService.updateBattleLog(lastLog);
        return responseMessage;
    }

    public ArrayList<String> calculateNonPlayerCharacterDamage(Long battleId) throws Exception {
        Battle battle = this.findBattleById(battleId);
        BattleLog lastLog = this.getLastBattleLogByBattleId(battleId);

        ArrayList<Integer> attackDices = DiceService.rollDices(battle.getEnemy().getDiceAmount(), battle.getEnemy().getDiceSides());
        Integer npcAttack = attackDices.stream().mapToInt(Integer::intValue).sum() + battle.getEnemyStrength();
        lastLog.setNonPlayerCharacterAttackDamage(npcAttack);
        Integer playerCharacterHealth = lastLog.getPlayerCharacterHealthStartOfTurn() - npcAttack;
        lastLog.setPlayerCharacterHealthEndOfTurn(playerCharacterHealth);
        String responseMessage;
        String playerStatus;
        if(playerCharacterHealth <= 0) {
            battle.setStatus(BattleStatus.PLAYER_CHARACTER_LOST);
            lastLog.setStatus(TurnStatus.FINISHED);
            this.updateBattle(battle);
            responseMessage = "The enemy cause " + npmDamage + " damage to you, reducing your HP from " + lastLog.getPlayerCharacterHealthStartOfTurn() + " to " + playerCharacterHealth + "\nYou lost the battle!";
            playerStatus = PlayerCharacterStatus.DEAD.toString();
        } else {
            responseMessage = "The enemy cause " + npmDamage + " damage to you, reducing your HP from " + lastLog.getPlayerCharacterHealthStartOfTurn() + " to " + playerCharacterHealth;
            playerStatus = PlayerCharacterStatus.ALIVE.toString();
        }
        this.battleLogService.updateBattleLog(lastLog);
        ArrayList<String> response = new ArrayList<>();
        response.add(responseMessage);
        response.add(playerStatus);
        return response;
    }
}
