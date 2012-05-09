/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.gameserver.model.zone.type;

import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.zone.L2ZoneRespawn;

/**
 * based on Kerberos work for custom L2CastleTeleportZone
 * @author Nyaran
 */
public class L2ResidenceTeleportZone extends L2ZoneRespawn
{
	private int _residenceId;
	
	public L2ResidenceTeleportZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("residenceId"))
		{
			_residenceId = Integer.parseInt(value);
		}
		else
		{
			super.setParameter(name, value);
		}
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		character.setInsideZone(L2Character.ZONE_NOSUMMONFRIEND, true); // FIXME: Custom ?
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(L2Character.ZONE_NOSUMMONFRIEND, false); // FIXME: Custom ?
	}
	
	@Override
	public void onDieInside(L2Character character)
	{
	}
	
	@Override
	public void onReviveInside(L2Character character)
	{
	}
	
	/**
	 * Returns all players within this zone
	 * @return
	 */
	public List<L2PcInstance> getAllPlayers()
	{
		List<L2PcInstance> players = new FastList<L2PcInstance>();
		
		for (L2Character temp : getCharactersInsideArray())
		{
			if (temp != null && temp.isPlayer())
				players.add(temp.getActingPlayer());
		}
		
		return players;
	}
	
	public void oustAllPlayers()
	{
		if (_characterList == null || _characterList.isEmpty())
			return;
		for (L2Character character : getCharactersInsideArray())
		{
			if (character != null && character.isPlayer())
			{
				L2PcInstance player = character.getActingPlayer();
				if (player.isOnline())
				{
					player.teleToLocation(getSpawnLoc(), 200);
				}
			}
		}
	}
	
	public int getResidenceId()
	{
		return _residenceId;
	}
}