# Sist-Distribuidos

Pequeno sistema distribuído de servidores utilizando sockets.

## Diagrama
![](diagrama.png "Diagrama do projeto")

## Decisões de projeto
Utilizou-se Sockets para conexão, Threads para comportar vários clientes no mesmo servidor e uma lista para decidir qual servidor escolher.

Dependendo da mensagem o servidor (SwitchServer) encaminha o pedido ao servidor escravo neccessário. O servidor a frente da Lista recebe sua operação e é movido ao fim da lista. 

As portas foram escolhidas com enfase na memorização, com cuidado para evitar portas <= 1024. O servidor de operações básicas é indentificado pelas portas >= a 7000. Enquanto que os servidores de operações especiais são identificados por portas <= 6999.

Lista de portas: 
  * SimpleJavaClient [6666]
  * SwitchServer 	 [6666]
  * ServerOpbase >= [7000]
  * ServerOpspec <=	[6999]
