import styled from 'styled-components'
import React from "react";
import YellowButton from "../buttons/YellowButton";

export default function Specialist(props) {
    console.log(props);
    return (
        <Wrapper>
            <Header>
                <AvatarContainer>
                    <img src='/images/avatar-2.png'
                           alt='Нет аватара'
                    />
                </AvatarContainer>
            </Header>
            <Body>
                <div>
                    <h2>{props.title || 'Work with us and forget about others'}</h2>
                </div>
            </Body>
            <Footer>
            <NameContainer>
                <FlexColumn>
                    {props.name || 'Full name'}
                    <DateContainer>
                    {props.date || '33 March, 20'}
                    </DateContainer>
                </FlexColumn>
            </NameContainer>

            <ButtonContainer>
            <YellowButton text="Follow"/>
            </ButtonContainer>

            </Footer>
        </Wrapper>
    )
};

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    background-color: #FFF3F8;
    color: #18191F;
    border: 2px solid #18191F;
    padding: 24px;
    height: 396;
    width: 327px;
    border-radius: 12px;
    text-align: left;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    box-shadow: 0px 6px 0px #18191F;
  margin-bottom: 30px;
  margin-right: 35px;
`;

const Header = styled.div`
    display: flex;
  align-items: center;
  margin-bottom: 20px;
`;

const AvatarContainer = styled.div`
    display: flex;
    width: 60px;
    height: 60px;
`;

const NameContainer = styled.div`
align-self: center;
  margin-top: 10px;
`;

const Body = styled.div`
display: flex;
flex-direction: column;
font-style: normal;
font-weight: bold;
font-size: 30px;
line-height: 40px;
`;

const Footer = styled.div`
    display: flex;
    justify-content: space-between;
    margin-top: 56px;
`;

const Message = styled.div`
    display: flex;
    margin-left: 33px;
    margin-right: 5px;
`;

const UsersCount = styled.div`
margin-right: 5px;
`;

const Description = styled.div`
color: #454A57;
margin-top: -25px;
`;

const FlexRow = styled.div`
display: flex;
flex-direction: row;
padding-bottom: 5%;
`;

const FlexColumn = styled.div`
display: flex;
flex-direction: column;
`;

const DateContainer = styled.div`
display: flex;
flex-direction: column;
font-style: normal;
font-weight: 500;
font-size: 13px;
line-height: 18px;
`;

const ButtonContainer = styled.div`
display: flex;
`;