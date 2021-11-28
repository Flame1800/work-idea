import Head from 'next/head'
import styled from 'styled-components'
import React from "react";
import Image from "next/image";
import Ideas from "../components/Cards/Ideas";
import Specialist from "../components/Cards/Specialist";
import BlackButton from "../components/buttons/BlackButton";
import WhiteButton from "../components/buttons/WhiteButton";
import OrangeButton from "../components/buttons/OrangeButton";
import Header from "../components/Header";
import Link from "next/link";


export default function Home() {

    return (
        <Wrapper className="container">
            <Head>
                <title>Create Next App</title>
                <link rel="icon" href="/favicon.ico"/>
            </Head>
            <Header />

            <main>
                <FlexColumn>
                    <FlexRow>
                        <div>
                            <H2>Лучший способ найти команду и реализовать свой проект</H2>
                            <H2></H2>
                            <Btn>
                                <OrangeButton text="Начать знакомство"/>
                            </Btn>
                        </div>
                        <PicturesContainers>
                            <img
                                src='/images/peep-standing-18.png'
                                width={230}
                                height={400}
                                alt='Нет картинки'
                            />

                        </PicturesContainers>
                    </FlexRow>
                    <FlexRow>
                        <Number>
                            <div>01</div>
                            <div>
                                ________________________
                            </div>


                        </Number>
                        <Info1>
                            <h3>Находите интересные для вас проекты</h3>
                            <Text1>Или предлгайте свои идеи</Text1>
                        </Info1>
                    </FlexRow>
                    <FlexRow>
                        <CardContainer>
                            <Ideas/>
                            <Ideas/>
                            <Ideas/>
                        </CardContainer>
                    </FlexRow>
                    <FlexRow>
                    <Number>
                            <div>02</div>
                            <div>
                                ________________________
                            </div>

                            
                            <FlexRow>
                            <Info2>
                            <h3>Удобный подбор специалистов</h3>
                            <Text2>Можете с найти нужных вам людей и общаться с ними</Text2>
                            </Info2>
                            </FlexRow>
                        </Number>
                    </FlexRow>

                    <FlexRow>
                        <CardContainer2>
                            <Specialist/>
                            <Specialist/>
                            <Specialist/>
                        </CardContainer2>
                    </FlexRow>

                        <Number>
                            <div>03</div>
                            <div>
                                ________________________
                            </div>

                            
                            <FlexRow>
                            <Info2>
                            <h3>Создайте свою идею или реализуйте проект из уже готовой идеи</h3>
                            <Text3>Наша задумка позволяет подобрать интересные идеи которые оставляют пользователи на сервисе</Text3>
                            </Info2>
                            </FlexRow>
                        
                        </Number>



                        <JoinUs>
                        <FlexRow>
                            <FlexColumn>
                            <JoinUsText>
                            <h3>Присоединяйся!</h3>
                            <Text3>Если ты хочешь реализовать себя в интересных проектах, то добро пожаловать!</Text3>
                                 <Link href='/register'>
                                    <a>
                                        <JoinUsButton>
                                        <WhiteButton text="Зарегистрироваться" />
                            </JoinUsButton>
                                    </a>
                                </Link>

                                <Link href='/login'>
                                    <a>
                                        <JoinUsButton>
                                            <BlackButton text="Войти в WorkIdea" />
                                        </JoinUsButton>
                                    </a>
                                </Link>

                            </JoinUsText>
                            </FlexColumn>
                            <FlexColumn>
                            <Image
                                src='/images/placeholder_1.png'
                                width={354}
                                height={354}
                                alt='Нет картинки'
                            />
                            </FlexColumn>
                        </FlexRow>
                        </JoinUs>
                </FlexColumn>
            </main>
        </Wrapper>
    )
}

const Wrapper = styled.div`
display: flex;
flex-direction: column;
max-width: 70%;
margin-left: 15%;
`;

const Info1 = styled.div`
margin-top: -100px;
margin-right: 15%;
font-size: 44px;
`;

const Info2 = styled.div`
align-self: left;
font-size: 44px;
width: 50%;
margin-bottom: 50px;
`;


const Text1 = styled.div`
font-size: 21px;
margin-right: 40%;
`;

const Text2 = styled.div`
font-size: 21px;
width: 300px;
margin-top: -45%;
margin-left: 140%;
`;

const Text3 = styled.div`
font-size: 21px;
width: 300px;
margin-bottom: 40px;
`;



const Flex = styled.div`
display: flex;
`;

const FlexRow = styled.div`
display: flex;
flex-direction: row;
  margin-bottom: 100px;
`;

const FlexColumn = styled.div`
display: flex;
flex-direction: column;
`;

const H2 = styled.h2`
  font-size: 42px;
  font-weight: 900;
  margin-top: 100px;
  margin-right: -30px;
  z-index: 9;
  position: relative;
  animation: mymove 1s;

  /* Стандартный синтаксис */
  @keyframes mymove {
    0% {opacity: 0;
    margin-top: 10px;}
  }
`

const Btn = styled.div`
  animation: mymove 1s;

  /* Стандартный синтаксис */
  @keyframes mymove {
    0% {opacity: 0;
    margin-top: 10px;}
  }
`

const PicturesContainers = styled.div`
  position: relative;
  display: flex;
  margin-left: 400px;
  margin-top: 100px;
  animation: mymove 1s;

  /* Стандартный синтаксис */
  @keyframes mymove {
    0% {opacity: 0;
    margin-top: 10px;}
}
`;

const Number = styled.div`
padding-right: 10%;
font-size: 2vw;
`;

const CardContainer = styled.div`
display: flex;
`;

const CardContainer2 = styled.div`
display: flex;
margin-left: 15%;
`;

const Reg = styled.button`

`;

const ButtonsContainers = styled.div`
display: flex;
justify-content: space-between;
width: 193px;
`;

const JoinUs = styled.div`
display: flex;
width: 60%;
margin-right: 20%;
margin-left: 20%;
`;

const JoinUsText = styled.div`
font-size: 21px;
display: flex;
margin-top: -20px;
margin-right: 20px;
flex-direction: column;
text-align: left;
`;

const JoinUsButton = styled.div`
margin-bottom: 20px;
align-self: right;
`

const HrFooter = styled.div`
width: 140%;
margin-left: -20%;
height: 2px;
background-color: #18191F;
margin-bottom: 20px;
`