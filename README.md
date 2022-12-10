<h1 align="center">🤖 Uai!rror | Speed Line Follow 🔥</h1>
<p align="center">Aplicativo Android desenvolvido para a equipe de robótica Uai!rror para telemetria de robôs seguidores de linha.</p>

## 📋 Sobre
<p align="justify">
Em geral, robôs seguidores de linha utilizam controle PID e umas das melhores técnicas é testar as constantes em ação, mas sem uma ferramenta que agilize o processo isso se torna tedioso e cansativo (tirando toda a alegria que a robótica proporciona 😄). Pensando nisso, desenvolvi junto a equipe Uai!rror um App Android, que se comunica via bluetooth com o robô, e muda parâmetros internos de sua programação quando o mesmo está correndo em pista. Gostaria de agradecer a <b>Lucas Martins 😎</b> pelo apoio prestado e o empréstimo de materiais, desejo muito sucesso em suas competições.
</p>

<div align="center">
  <img width="300" src="img/img2.jpg"/>
  <img width="300" src="img/img3.jpg"/>
</div>

## 😉 Como usar o App
✔ Inicialmente baixe o arquivo <b>uairror-speed-line-follow.apk</b> e instale no seu dispositivo Android<br>
<div align="center">
  <img width="300" src="img/instalar/img1.jpg"/>
  <img width="300" src="img/instalar/img2.jpg"/>
  <img width="300" src="img/instalar/img3.jpg"/>
  <img width="300" src="img/instalar/img4.jpg"/>
</div>
✔ Daí vocês deve parear o seu módulo HC-05 com o aparelho celular<br>
<div align="center">
  <img width="300" src="img/img6.jpg"/>
</div>
✔ Em seguida você deve entrar no App e permitir o bluetooth<br>
<div align="center">
  <img height="300" src="img/instalar/img5.jpg"/>
</div>
✔ Depois você pode utilizar as barras de rolagem horizontal ou os campos de textos para enviar seus dados (as barras horizontais fazem envio dos dados automaticamente, já nos campos de texto você deve clicar no botão do avião de papel para enviar)<br>
<div align="center">
  <img height="300" src="img/instalar/img6.jpg"/>
</div>

## 📁 uairror-speed-line-follow-test
<p align="justify">
Dentro dessa pasta temos o código fonte do arduino, com a finalidade de testar o app. Esse código recebe dados enviados pelo App através do Módulo HC-05. Tais dados são separados em blocos para melhor comunicação. Uma vez que os dados são recebidos e separados, os respectivos parâmetros são alterados em tempo de execução. Parece mágica 🤣 mas é apenas o Socket trabalhando.
</p>

<img src="img/img1.jpg"/>

## ❓ Explicando os Botões
<p align="justify">
Temos 4 botões, cada um com sua funcionalidade. A lâmpada tem a função de ligar e desligar o robô. O bluetooth tem a função de conectar ou desconectar o App com o Módulo bluetooth ( cabe dizer que o módulo bluetooth tem que estar pareado para poder ser conectado ). O avião de papel tem a função de enviar os 4 parâmetros que estão na forma de Caixa de Texto (CACE, CDES, VMAX, VMIN). Por fim o botão de atualizar que muda todos os parâmetros dos App para os parâmetros que existem no robô ( então ele envia uma solicitação para o robô enviar os dados que estão nele atualmente, recomendo utilizar logo após conectar com o robô )
</p>

<div align="center">
  <img src="img/img5.jpg"/>
</div>

## ❔ Explicando os Parâmetros
<p align="justify">
Quando falamos em comunicação quando menos bytes enviamos melhor, então para isso criamos uma tabela de códigos apenas uma letra vai ditar o parâmetro que deve ser alterado no momento. Parece complicado? 🤔 Mas olha a imagem abaixo. Podemos ver que cada valor que pode ser alterado tem um letra correspondente, e no final a mensagem será enviada com um caractere para sinalizar o início da mensagem ( no caso o <b>{</b> ) um letra que representa um parâmetro junto ao novo valor que deve ser atribuído a esse tal parâmetro e por fim um caractere para simbolizar o final da mensagem ( no caso o <b>}</b> ) 
</p>

<div align="center">
  <img src="img/img4.jpg"/>
</div>

## 📁 project
Nessa pasta temos os códigos fontes da aplicação feita em Java no Android Studio IDE assim com um <b>.zip</b> com o projeto de fato.
1) <b>ListaDispositivos:</b> <i>Classe que cuida da obtenção dos dispositivos pareados e cria uma lista com esse dispositivo para ser selecionada pelo usuário.</i><br>
2) <b>MainActivity:</b> <i>Classe principal, contém tudo relativo ao fluxo da aplicação, método para botões, eventos para SeekBar, etc.</i><br>
3) <b>Mensagem:</b> <i>Classe auxiliar para tratar os envios de mensagem para o usuário, como Toast e AlertDialog.</i><br>
4) <b>ThreadConexao:</b> <i>Classe que trata da criação e conexão da Thread que roda paralelamente ao fluxo da aplicação principal. Trata o envio e o recebimento de mensagem.</i><br>
5) <b>activity_main.xml:</b> Código XML do front-end da aplicação, nele estão todas as informações sobre os componente da parte visual da tela do App<br>



