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

## 📁 uairror-speed-line-follow-test
Dentro dessa pasta temos o código fonte do arduino, com a finalidade de testar o app. Esse código recebe dados enviados pelo App através do Módulo HC-05. Tais dados são separados em blocos para melhor comunicação. Uma vez que os dados são recebidos e separados, os respectivos parâmetros são alterados em tempo de execução. Parece mágica 🤣 mas é apenas o Socket trabalhando.

<img src="img/img1.jpg"/>

## ❔ Explicando os Parametros
<p align="justify">
Quando falamos em comunicação quando menos bytes enviamos melhor, então para isso criamos uma tabela de códigos apenas uma letra vai ditar o parâmetro que deve ser alterado no momento. Parece complicado? 🤔 Mas olha a imagem abaixo. Podemos ver que cada valor que pode ser alterado tem um letra correspondente, e no final a mensagem será enviada com um caractere para sinalizar o início da mensagem ( no caso o <b>{</b> ) um letra que representa um parâmetro junto ao novo valor que deve ser atribuído a esse tal parâmetro e por fim um caractere para simbolizar o final da mensagem ( no caso o <b>}</b> ) 
</p>

<div align="center">
  <img src="img/img4.jpg"/>
</div>

## ❓ Explicando os Botões
<p align="justify">
Temos 4 botões, cada um com sua funcionalidade. A lâmpada tem a função de ligar e desligar o robô. O bluetooth tem a função de conectar ou desconectar o App com o Módulo bluetooth ( cabe dizer que o módulo bluetooth tem que estar pareado para poder ser conectado ). O avião de papel tem a função de enviar os 4 parâmetros que estão na forma de Caixa de Texto (CACE, CDES, VMAX, VMIN). Por fim o botão de atualizar que muda todos os parâmetros dos App para os parâmetros que existem no robô ( então ele envia uma solicitação para o robô enviar os dados que estão nele atualmente, recomendo utilizar logo após conectar com o robô )
</p>

<div align="center">
  <img src="img/img5.jpg"/>
</div>
