[USCKI Incognito](http://www.uscki.nl/) bouwt een [robot](http://robot.uscki.nl/), om dezelfde reden dat mensen kinderen maken. Het zal met liefde uit de bits geboren worden, opgroeien temidden de zorg van een fantastische vereniging, en leren van diens toegewijde leden. Het zal met vallen en opstaan, spelenderwijs en streng doch rechtvaardig opgevoed worden. Zoals trotse ouders dat ook doen, hopen wij dat het ooit zal kunnen gaan studeren. En dan lid worden van onze mooie vereniging.

# Voor het nageslacht!
*De code minimaal werkend krijgen op je eigen computer*

0. Zorg dat je een beetje Java kan en met een terminal overweg kan. Volg een cursus Cyberspace voor Senioren als dat nodig is.

1. Zorg dat je een java-ontwikkelomgeving werkend heb op je computer (`jdk`). Dit betekent dat `java` en `javac` het moeten doen in je terminal.

2. Download de code. Handmatig downloaden (niet aangeraden), of...<br />
	...als je meedoet en rechten hebt gekregen: Clone met [git](https://help.github.com/articles/set-up-git): `git clone <zo'n git url hierboven>`<br />
	...als je nog niet meedoet: fork het project en doe later een pull request<br />
	en navigeer je terminal naar de hoofdmap.
	
3. Doe het automagisch en installeer [apache ant](http://ant.apache.org) of WinAnt voor Windows

		ant test
	
	of doe het handmatig
	
		mkdir build
		javac -d build -cp src/:lib/ src/mennov1/ThuisTester.java
		java -cp build/classes mennov1.ThuisTester
	
	of doe het in Eclipse
	
		?? (zorg iig dat je bouwt via ant, of alles in `lib/` in je Build Path (RMB op lib/*.jar -> Build Path -> Add To BuildPath) hebt staan)

4. Test wat dingen door te chatten
	
		Load Echobot
		Load Hogerlager

# Wereldoverheersing
*Meer chique dingen en bijdragen aan het robotproject*

1. Kijk of je de webcam in `src/mennov1/ThuisTester.java` kan oncommentariseren, en testen of de webcam via OpenCV het doet.
	OpenCV (versie 2.4) moet je zelf geinstalleerd hebben en er moet een werkende `.jar` in `lib/` staan voor je besturingssysteem.
	
	• Op ubuntu/debian `sudo apt-get install opencv` (of `sudo apt-get install libcv-dev libcvaux-dev libhighgui-dev`) runnen, maar er staat alleen nog voor de Raspberry Pi (die een ARM-chip heeft) eer `.jar` in.<br/>
	• Op de mac kan je het beste OpenCV downloaden via macports <br/>
	• Op windows handmatig downloaden en dan in je PATH zetten <br/>
		32 bit: `;C:\opencv\build\x86\vc10\bin;C:\opencv\build\common\tbb\ia32\vc10` <br/>
		64 bit: `;C:\opencv\build\x64\vc10\bin;C:\opencv\build\common\tbb\intel64\vc10`

2. Schrijf een bot zoals `bots/Echobot.java` (zie ook: nog niet bestaande documentatie)

3. Als je rechten heb gekregen van het team, kan je committen en pushen met git. [github help](https://help.github.com/articles/set-up-git) [gitref](http://gitref.org/)
		
		git commit -am "Cool ding geschreven"
		git pull
		git status
		git push

4. **Alles wat je hierheen commit verschijnt meteen bij het herstarten live op de Raspberry Pi!**
	TODO: aparte release-branch maken.

# WTF how does this even

- [Homepage met logs](http://robot.uscki.nl/)

- [Documentatie](http://uscki.github.com/robot)

- [Facebook](https://www.facebook.com/menno.veen.3)

# Toedoeeee

[x] Gezichtsdetectie

[ ] Interface naar uscki.nl voor KENNISCH

[ ] Makkelijker bots toevoegen

[ ] Facebook chat

[ ] Motor-aansturing voor werkbrauwen, hoofd draaien, etc

[ ] Gezichtsherkenning

[ ] Prolog
