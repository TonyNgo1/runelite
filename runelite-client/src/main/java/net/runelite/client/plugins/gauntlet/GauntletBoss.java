/*
 * Copyright (c) 2019, Siraz <https://github.com/Sirazzz>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.gauntlet;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.HeadIcon;
import net.runelite.api.NPC;
import net.runelite.api.ProjectileID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GauntletBoss
{
	private static final int CRYSTALLINE_HUNLLEF_CRYSTAL_ID = 9025;
	private static final int CORRUPTED_HUNLLEF_CRYSTAL_ID = 9039;

	// Projectile IDs
	protected static final int GAUNTLET_BOSS_MAGIC = 1707;
	protected static final int CORRUPTED_GAUNTLET_BOSS_MAGIC = 1708;
	protected static final int GAUNTLET_BOSS_RANGED = 1711;
	protected static final int CORRUPTED_GAUNTLET_BOSS_RANGED = 1712;
	protected static final int GAUNTLET_BOSS_MAGIC_DISABLE_PRAYERS = 1713;
	protected static final int CORRUPTED_GAUNTLET_BOSS_MAGIC_DISABLE_PRAYERS = 1714;

	// Anim IDs
	protected static final int GAUNTLET_BOSS_CRYSTAL_ATTACK = 8418;
	protected static final int GAUNTLET_BOSS_ATTACK = 8419;
	protected static final int GAUNTLET_BOSS_TRAMPLE = 8420;

	protected static final List<Integer> PLAYER_MELEE_ANIMATIONS = Arrays.asList(
			395, // Axe Slash
			401, // Axe Crush

			400, // Pick Crush
			401, // Pick Stab

			386, // Harpoon Stab
			390, // Harpoon Slash

			422, // Unarmed Punch
			423, // Unarmed Kick

			401, // Crystal Scepter
			428, // Crystal Halberd Jab & Fend
			440 // Crystal Halberd Swipe
	);

	protected static final List<Integer> PLAYER_RANGE_ANIMATIONS = Arrays.asList(
			426 // Crystal Bow
	);

	protected static final List<Integer> PLAYER_MAGE_ANIMATIONS = Arrays.asList(
			1167 // Crystal Staff
	);

	static final int ATTACK_RATE = 5; // 5 ticks between each attack
	static final int ATTACKS_PER_SWITCH = 4; // 4 attacks per style switch

	static final int PROTECTS_PER_SWITCH = 6; // 6 attacks received per prayer style switch

	static final int CRYSTAL_TICKS = 20;

	enum AttackStyle
	{
		MAGIC,
		RANGED
	}

	@Getter
	private NPC npc;

	@Getter
	@Setter
	private GauntletBoss.AttackStyle currentAttackStyle;

	@Getter
	@Setter
	private HeadIcon currentProtectStyle;

	@Getter
	@Setter
	private List<NPC> crystalEffects;

	@Getter
	@Setter
	private int crystalTicksLeft;

	@Getter
	@Setter
	private int attacksUntilSwitch;

	@Getter
	@Setter
	private int protectsUntilSwitch;

	@Getter
	@Setter
	private int nextAttackTick;

	@Getter
	@Setter
	private int lastAttackTick;

	@Getter
	@Setter
	private int recentProjectileId;

	@Getter
	@Setter
	private int remainingProjectileCount;

	@Getter
	@Setter
	private boolean changedAttackStyleThisTick;

	@Getter
	@Setter
	private boolean changedAttackStyleLastTick;

	@Getter
	@Setter
	private int lastTickAnimation;

	GauntletBoss(NPC npc)
	{
		this.npc = npc;
		this.lastAttackTick = -100;
		this.nextAttackTick = -100;
		this.recentProjectileId = -1;
		this.attacksUntilSwitch = ATTACKS_PER_SWITCH;
		this.protectsUntilSwitch = PROTECTS_PER_SWITCH;
		this.currentAttackStyle = AttackStyle.RANGED;
		this.crystalTicksLeft = 0;
		this.crystalEffects = new ArrayList<>();
	}

	boolean isGauntletBossRangedAttack(int projectileId)
	{
		return projectileId == GAUNTLET_BOSS_RANGED ||
				projectileId == CORRUPTED_GAUNTLET_BOSS_RANGED;
	}

	boolean isGauntletBossMagicAttack(int projectileId)
	{
		return projectileId == GAUNTLET_BOSS_MAGIC ||
				projectileId == GAUNTLET_BOSS_MAGIC_DISABLE_PRAYERS ||
				projectileId == CORRUPTED_GAUNTLET_BOSS_MAGIC ||
				projectileId == CORRUPTED_GAUNTLET_BOSS_MAGIC_DISABLE_PRAYERS;
	}

	boolean isNpcCrystalAttack(int npcId)
	{
		return npcId == CRYSTALLINE_HUNLLEF_CRYSTAL_ID || npcId == CORRUPTED_HUNLLEF_CRYSTAL_ID;
	}
}
