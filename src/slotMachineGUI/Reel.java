package slotMachineGUI;

import java.util.ArrayList;

public class Reel {

	int lblShowingValue1;
	int lblShowingValue2;
	int lblShowingValue3;

	ArrayList<Symbol> reelImg = new ArrayList<Symbol>();
	
	// declare the images into symbol class
	Symbol seven = new Symbol("Images/redseven.png", 7);
	Symbol bell = new Symbol("Images/bell.png", 6);
	Symbol cherry = new Symbol("Images/cherry.png", 2);
	Symbol lemon = new Symbol("Images/lemon.png", 3);
	Symbol plum = new Symbol("Images/plum.png", 4);
	Symbol watermelon = new Symbol("Images/watermelon.png", 5);
	
	// add images into array list
	public Reel(){
		reelImg.add(seven);
		reelImg.add(bell);
		reelImg.add(cherry);
		reelImg.add(lemon);
		reelImg.add(plum);
		reelImg.add(watermelon);

	}

	public Symbol[] spin() {
			
		Symbol[] reelImgShuffled = new Symbol[6];

		int[] swapped = new int[6];
		int[] added = new int[6];

		int num = 0;

		int random1 = 0;
		int random2 = 0;

		for (int i = 0; i < 6; i++)
			swapped[i] = 55;
		for (int i = 0; i < 6; i++)
			added[i] = 55;

		do {
			random1 = (int) (Math.random() * 6);
			random2 = (int) (Math.random() * 6);

			boolean isChanged = false;

			if (num == 5) {
				for (int y = 0; y < 6; y++) {

					if (reelImgShuffled[y] == null) {
						for (int i = 0; i < 6; i++) {
							if (reelImg.get(y) == reelImgShuffled[i]) {
								random2 = y;

								break;
							}
						}

						reelImgShuffled[y] = reelImg.get(y);
						swapped[num] = y;
						added[num] = y;
						num++;
						break;
					}
				}
			}

			if (random1 != random2) {

				for (int x = 0; x < swapped.length; x++) {
					if (random2 != swapped[x] && random1 != added[x])
						isChanged = false;
					else {
						isChanged = true;
						break;
					}
				}

				if (!isChanged) {
					reelImgShuffled[random2] = reelImg.get(random1);
					swapped[num] = random2;
					added[num] = random1;
					num++;
				}

			}

		} while (num < 6);

		return reelImgShuffled;


	}


}
