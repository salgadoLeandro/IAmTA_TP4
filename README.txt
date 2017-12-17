----------------------
 Projecto de IATA nº4
----------------------

----------------------
 Execução do Programa
 pela 1º vez
----------------------

O utilizador terá de gerar um ficheiro de pontos a partir do seu client_secret.json. Para tal, deverá correr o programa com os argumentos '-g <nome do json>.json' o que levará a geração de um ficheiro .ups com o número de passos ao longo dos dias.

----------------------
 Execução do Programa
----------------------

Depois de gerado o ficheiro .ups o utilizador pode correr o programa sem os argumentos supramencionados.

--------------------
 Decisões de Design
--------------------

Neste projecto, foi decidido que não se iria usar os ficheiros json de modo a evitar a partilha das chaves das APIs. A partir dos ficheiros com passos o programa determina os pontos e os achievements do utilizador.
Por último, o ranking dos utilizadores é apresentado na consola.