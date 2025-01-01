package controller;

import models.HorlogeEvent;

public interface HorlogeEventListener {
	void finHorloge(HorlogeEvent evt);
	void updateHorloge(HorlogeEvent evt);
}