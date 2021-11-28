import styled from 'styled-components'
import React, {useEffect, useState} from "react";
import Link from "next/link";
import YellowButton from "../buttons/YellowButton";
import Api from "../../api/api";
import OrangeButton from "../buttons/OrangeButton";
import WhiteButton from "../buttons/WhiteButton";
import PinkButton from "../buttons/PinkButton";
import {useAppSelector} from "../../redux/hooks";
import Like from "../Like";

export default function Ideas({idea}) {
    const [user, setUser] = useState();

    useEffect(async () => {
        setUser(await Api.getUser(idea?.id))
    }, []);
    return (
        <Wrapper>
            <Header>
                <Profile>
                    <Link href={`/profile/${idea?.Author?.id || user?.data?.id}`} >
                        <a>
                            <AvatarContainer>
                                <img src='/images/avatar-2.png'
                                     alt='Нет аватара'
                                />
                            </AvatarContainer>
                        </a>
                    </Link>
                    <Link href={`/profile/${idea?.Author?.id || user?.data?.id}`} >
                        <a>
                            <NameContainer>
                                {idea?.Author.username || user?.data?.username || 'Lamborci Mona'}
                            </NameContainer>
                        </a>
                    </Link>

                </Profile>
                <div style={{ display: 'flex' }}>
                    <Link  href={`/ideas/${idea?.id}`}>
                        <a>
                            <YellowButton text='Открыть идею'/>
                        </a>
                    </Link>
                    <Like />
                </div>
            </Header>
            <Body>
                <div>
                    <h2>{idea?.minifiedDescription || 'Какая-нибудь интересная идея'}</h2>
                </div>
                <Description>
                    <p>
                        {idea?.fullDescription || 'If you have small description you should write here.'}
                    </p>
                </Description>
            </Body>
            <Footer>
                <Message>
                    <img
                        src='/images/message-circle.png'
                        alt='нет картинки'
                    />
                </Message>
                {idea?.idea_comments?.length || 0}
            </Footer>
        </Wrapper>
    )
};

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    background-color: #E9E7FC;
    color: #18191F;
    border: 2px solid #18191F;
    padding: 24px;
    height: auto;
    width: 827px;
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
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 20px;
`;

const AvatarContainer = styled.div`
    width: 32px;
    height: 32px;
`;

const NameContainer = styled.div`
align-self: center;
margin-left: 32px;
  margin-top: 10px;
`;

const Body = styled.div`
display: flex;
flex-direction: column;
`;

const Footer = styled.div`
    display: flex;
    align-items: center;
    margin-top: 56px;
`;

const Message = styled.div`
    display: flex;
    margin-right: 5px;
`;

const UsersCount = styled.div`
margin-right: 5px;
`;

const Description = styled.div`
color: #454A57;
margin-top: -25px;
`;

const Profile = styled.div`
  display: flex;
`;
