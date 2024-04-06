package model.orders;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import model.Country;
import model.Order;
import model.Player;
import observerpattern.LogEntryBuffer;

/**
 * The Advance class is a type of an Order issued by a Player. This Order
 * provides the ability for a player
 * to attack a territory belonging to some other player with some number of
 * armies. The attacker has a probability of 60%
 * to win an attack while the defender has a probability of 70% to win an
 * attack. The issued order is in the form of
 * "advance sourceCountry targetCountry NumberOfArmies".
 *
 */
public class Advance implements Order {
	/**
	 * Logger object to logs the vents in the log file
	 */
	LogEntryBuffer d_Leb = new LogEntryBuffer();
	/**
	 * The sourceCountry of the attacking player and the targetCountry of the
	 * Defending Player.
	 */
	Country d_SourceCountry;
	/**
	 * The sourceCountry of the attacking player and the targetCountry of the
	 * Defending Player.
	 */
	Country d_TargetCountry;
	/**
	 * The issuing or the attacking player.
	 */
	Player d_Player;
	/**
	 * The number of armies with which the player plans to attack.
	 */
	int d_NumArmies;

	/**
	 * Default Constructor
	 */
	public Advance() {

	}

	/**
	 * 
	 * @param p_Player        The player that is issuing the order - The attacker.
	 * @param p_SourceCountry The Country from which armies are to be moved for
	 *                        attack
	 * @param p_TargetCountry The country which is to be attacked.
	 * @param p_NumArmies1    The number of armies going for attack.
	 */

	public Advance(Player p_Player, Country p_SourceCountry, Country p_TargetCountry, int p_NumArmies1) {
		d_Player = p_Player;
		d_SourceCountry = p_SourceCountry;
		d_TargetCountry = p_TargetCountry;
		d_NumArmies = p_NumArmies1;

	}

	/**
	 * This is an overridden execute method of Order interface. This method
	 * implements the attack logic.
	 * The logic implemented depends on the integer value returned from the isValid
	 * method.
	 * <ol>
	 * <li>If the sourceCountry and the targetCountry both belong to the issuing
	 * player then the armies
	 * simply move from the source to the target country.</li>
	 * <li>If the target country does not have any number of armies, then the that
	 * territory is simply acquired by the issuing player
	 * and he/she deploys the attacking number of armies at the target Country.</li>
	 * <li>Else the attack takes place.</li>
	 * </ol>
	 * Attack - Each attacking army is assigned a random integer between 0-6 (that
	 * shows the 60% probability of it winning the attack).
	 * Each defending army is assigned a random integer between 0-7 (that shows the
	 * 70% probability of it surviving the attack).
	 * If the attacking number of armies are greater than the defending number of
	 * armies then, the attacker takes the best armies
	 * equal to the number of defending armies to fight with and vice versa. in the
	 * battleground, armies fight one on one. If the
	 * assigned number of the attacker army is greater than the assigned number of
	 * the defender army then, that particular
	 * attacker army wins and the same goes the other way round. At the end if the
	 * count of winning attacker armies is more than
	 * the attacker wins the country and deploys the armies remaining after the
	 * fight and the armies that were kept aside on the
	 * target country that didn't participate in the fight. If the count of winning
	 * armies of attacker is same or less than that
	 * of the defender than the defender wins. In such a case the target country has
	 * the left defending armies after the fight
	 * plus those armies of the defender that didn't participate in the fight. And
	 * the remaining armies of the attacker are sent
	 * back to its source Country.
	 */
	@Override
	public void execute() {

		Random l_Rand = new Random();
		int l_Flag = isValid();
		if (l_Flag == 1) {
			d_SourceCountry.setNoOfArmies(d_SourceCountry.getNoOfArmies() - d_NumArmies);
			d_TargetCountry.setNoOfArmies(d_TargetCountry.getNoOfArmies() + d_NumArmies);
		}
		if (l_Flag == 2) {
			if (d_TargetCountry.getNoOfArmies() == 0) {
				d_TargetCountry.getCountryOwnerPlayer().removeCountry(d_TargetCountry);
				d_TargetCountry.setCountryOwnerPlayer(d_Player);
				d_TargetCountry.setNoOfArmies(d_NumArmies);
				d_SourceCountry.setNoOfArmies(d_SourceCountry.getNoOfArmies() - d_NumArmies);
				d_Player.addCountry(d_TargetCountry);
				d_Player.setAtleastOneBattleWon(true);

				d_Player.setResult("\n" + d_Player.getPlayerName() + " your attack on "
						+ d_TargetCountry.getCountryName() + " was a Success!!");
				return;
			}
			if ("strategypattern.CheaterPlayerStrategy".equals(d_Player.getPlayerStrategy().toString().split("@")[0])) {
				d_TargetCountry.getCountryOwnerPlayer().removeCountry(d_TargetCountry);
				d_TargetCountry.setCountryOwnerPlayer(d_Player);
				d_TargetCountry.setNoOfArmies(d_NumArmies);
				d_SourceCountry.setNoOfArmies(d_SourceCountry.getNoOfArmies() - d_NumArmies);
				d_Player.addCountry(d_TargetCountry);
				d_Player.setAtleastOneBattleWon(true);

				d_Player.setResult("\n" + d_Player.getPlayerName() + " your attack on "
						+ d_TargetCountry.getCountryName() + " was a Success!!");
				return;
			}
			TreeMap<Integer, Integer> l_AttackerArmies = new TreeMap<>();
			TreeMap<Integer, Integer> l_DefenderArmies = new TreeMap<>();
			TreeMap<Integer, Integer> l_AttackerArmiesinHand = new TreeMap<>();
			TreeMap<Integer, Integer> l_DefenderArmiesinHand = new TreeMap<>();

			// Assigning random values to the armies.
			for (int i = 0; i < d_NumArmies; i++) {
				l_AttackerArmies.put(i, l_Rand.nextInt(6));
			}
			for (int i = 0; i < d_TargetCountry.getNoOfArmies(); i++) {
				l_DefenderArmies.put(i, l_Rand.nextInt(7));
			}
			l_AttackerArmiesinHand = l_AttackerArmies;
			l_DefenderArmiesinHand = l_DefenderArmies;

			// size difference between the attacking and defending armies
			int l_SizeDiff = l_AttackerArmies.size() - l_DefenderArmies.size();
			// Who has less number of armies.
			int l_MinArmies = Math.min(l_AttackerArmies.size(), l_DefenderArmies.size());
			System.out.println("min armies" + l_MinArmies);

			try {
				// Getting the armies to fight in battleground based on who has less number of
				// armies.
				TreeMap<Integer, Integer> l_ReturnedHashMap = ArmiestoFight(l_SizeDiff, l_MinArmies, l_AttackerArmies,
						l_DefenderArmies);
				System.out.println("returned hashmap " + l_ReturnedHashMap);
				Iterator<Map.Entry<Integer, Integer>> l_ItrAttacker = l_AttackerArmiesinHand.entrySet().iterator();
				Iterator<Map.Entry<Integer, Integer>> l_ItrDefender = l_DefenderArmiesinHand.entrySet().iterator();

				// When attacking armies are greater than defending armies
				if (l_SizeDiff > 0) {
					l_AttackerArmiesinHand = l_ReturnedHashMap;
					l_ItrAttacker = l_AttackerArmiesinHand.entrySet().iterator();
				}
				// When defending armies are greater than attacking armies.
				else if (l_SizeDiff < 0) {
					l_DefenderArmiesinHand = l_ReturnedHashMap;
					l_ItrDefender = l_DefenderArmiesinHand.entrySet().iterator();
				}
				// When both have equal number of armies
				else {
					l_AttackerArmiesinHand = l_AttackerArmies;
					l_DefenderArmiesinHand = l_DefenderArmies;
				}
				// now attack

				int l_AttackWin = 0;
				int l_DefendWin = 0;
				for (int i = 0; i < l_ReturnedHashMap.size(); i++) {
					Map.Entry<Integer, Integer> l_EntryAttack = l_ItrAttacker.next();
					Map.Entry<Integer, Integer> l_EntryDefend = l_ItrDefender.next();
					d_Leb.setResult("Attack Army : " + l_EntryAttack.getKey() + " with " + l_EntryAttack.getValue()
							+ " Value Vs.  Defend Army : " + l_EntryDefend.getKey() + " with "
							+ l_EntryDefend.getValue() + " Value");
					if (l_EntryAttack.getValue() >= l_EntryDefend.getValue()) {
						l_AttackWin++;
					} else {
						l_DefendWin++;
					}
				}
				if (l_AttackWin > l_DefendWin) {
					d_TargetCountry.getCountryOwnerPlayer().removeCountry(d_TargetCountry);
					d_TargetCountry.setCountryOwnerPlayer(d_Player);
					d_TargetCountry.setNoOfArmies(d_NumArmies - l_MinArmies + l_AttackWin);
					d_SourceCountry.setNoOfArmies(d_SourceCountry.getNoOfArmies() - d_NumArmies);
					d_Player.addCountry(d_TargetCountry);
					d_Player.setAtleastOneBattleWon(true);

					d_Player.setResult("\n" + d_Player.getPlayerName() + " your attack on "
							+ d_TargetCountry.getCountryName() + " was a Success!!");
				} else {
					d_SourceCountry.setNoOfArmies(d_SourceCountry.getNoOfArmies() - l_MinArmies + l_AttackWin);
					d_TargetCountry.setNoOfArmies(d_TargetCountry.getNoOfArmies() - l_MinArmies + l_DefendWin);
					d_Player.setResult("\n" + d_Player.getPlayerName() + " your attack on "
							+ d_TargetCountry.getCountryName() + " was a Failure!!");
				}
			} catch (Exception p_E) {
				p_E.printStackTrace();
			}
		}

	}

	/**
	 * This Method returns the armies to fight in the battle ground. If the original
	 * attacking number of armies is greater than the
	 * defending number of armies. Then the 1st n number of armies with greatest
	 * corresponding values are selected from the original
	 * set of armies. Where n would be the number of defending armies. Same happens
	 * when original defending number of armies is
	 * greater than the attacking number of armies. Then the 1st n number of armies
	 * with greatest corresponding values are selected
	 * from the original set of armies. Where n would be the number of attacking
	 * armies. In the case when both are equal in number
	 * then it just returns the attacking armies for size reference in execute
	 * method.
	 * 
	 * @param p_SizeDiffint    This integer shows which side of armies are greater
	 *                         in number.
	 * @param p_MinArmies      The number of armies that will participate in the
	 *                         fight.
	 * @param p_AttackerArmies The original attacking armies with the random numbers
	 *                         assigned to it.
	 * @param p_DefenderArmies The original defending armies with the random numbers
	 *                         assigned to it.
	 * @return The armies with maximum corresponding value of attacker side or
	 *         defender side depending on who has greater number of armies.
	 */

	TreeMap<Integer, Integer> ArmiestoFight(int p_SizeDiffint, int p_MinArmies,
			TreeMap<Integer, Integer> p_AttackerArmies, TreeMap<Integer, Integer> p_DefenderArmies) {
		TreeMap<Integer, Integer> l_ReturnHashMap = null;
		System.out.println("size difference" + p_SizeDiffint);

		// Attacking armies greater than defending armies.
		if (p_SizeDiffint > 0) {
			// Sorting the attacking armies on the basis of random values assigned in
			// descending order.
			List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(p_AttackerArmies.entrySet());
			Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
				@Override
				public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});

			TreeMap<Integer, Integer> l_AttackerArmiesinHand = new TreeMap<>();
			Iterator<Map.Entry<Integer, Integer>> itr = list.iterator();

			// Selecting top n armies to be sent to fight.
			for (int i = 0; i < p_MinArmies; i++) {
				Map.Entry<Integer, Integer> entry = itr.next();
				l_AttackerArmiesinHand.put(entry.getKey(), entry.getValue());
			}
			System.out.println("l_AttackerArmiesinHand" + l_AttackerArmiesinHand);
			l_ReturnHashMap = l_AttackerArmiesinHand;
		}

		// Defending armies greater than attacking armies.
		else if (p_SizeDiffint < 0) {
			// Sorting the attacking armies on the basis of random values assigned in
			// descending order.
			List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(p_DefenderArmies.entrySet());
			Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
				@Override
				public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});

			TreeMap<Integer, Integer> l_DefenderArmiesinHand = new TreeMap<>(Collections.reverseOrder());
			Iterator<Map.Entry<Integer, Integer>> itr = list.iterator();

			// Selecting top n armies to be sent to fight.
			for (int i = 0; i < p_MinArmies; i++) {
				Map.Entry<Integer, Integer> entry = itr.next();
				System.out.println(entry.getKey() + " : " + entry.getValue());
				l_DefenderArmiesinHand.put(entry.getKey(), entry.getValue());
			}
			System.out.println("l_DefenderArmiesinHand" + l_DefenderArmiesinHand);
			l_ReturnHashMap = l_DefenderArmiesinHand;
		}

		// When both have same number of armies.
		else {
			l_ReturnHashMap = p_AttackerArmies;
		}
		System.out.println("in armies to fight" + l_ReturnHashMap);
		return l_ReturnHashMap;
	}

	/**
	 * This method checks the validity of the order.
	 * The cases when the order is not valid are:
	 * <ol>
	 * <li>When the issuing player is in negotiation with the owner of the target
	 * country</li>
	 * <li>When the sourceCountry and the taretCountry are the same</li>
	 * <li>When sourceCountry and the targetCountry are not neighbours</li>
	 * <li>When the sourceCountry is left with armies less than one after the order
	 * has been issued with certain number of armies</li>
	 * </ol>
	 * An integer 1 is returned when both the countries belong to the issuing
	 * player.
	 * An integer 2 is returned when the country belongs to different players.
	 * 
	 * @return integer that shows different cases of execution if the order is valid
	 */

	public int isValid() {

		int l_ReturnInt = 0;
		if (d_Player.getNegotiatedPlayerList().contains(d_TargetCountry.getCountryOwnerPlayer())) {
			d_Player.setResult("\nThe targeted country " + d_TargetCountry.getCountryName() + " belongs to "
					+ d_TargetCountry.getCountryOwnerPlayer().getPlayerName() + " which is negotiated player!");
			l_ReturnInt = 0;
		} else if (d_SourceCountry == d_TargetCountry) {
			d_Player.setResult("\nThe source country and target country cannot be same!");
			l_ReturnInt = 0;
		} else if (!d_SourceCountry.getBorder().contains(d_TargetCountry.getCountryName())) {
			d_Player.setResult("\nThe source country and target country are not neighbours!");
			l_ReturnInt = 0;
		} else if (d_SourceCountry.getNoOfArmies() - d_NumArmies < 1) {
			d_Player.setResult("\nThe source country should be left with atleast one army!");
			l_ReturnInt = 0;
		} else {
			if (d_Player.getCountryList().contains(d_SourceCountry)
					&& d_Player.getCountryList().contains(d_TargetCountry)) {
				d_Player.setResult("\nThe source country and target country belong to the same player");
				l_ReturnInt = 1;
			} else if (d_Player.getCountryList().contains(d_SourceCountry)
					&& !d_Player.getCountryList().contains(d_TargetCountry)) {
				l_ReturnInt = 2;
			}

		}
		return l_ReturnInt;

	}
}
