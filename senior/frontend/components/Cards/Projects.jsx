import styled from 'styled-components';
import React, {useEffect, useState} from "react";
import YellowButton from "../buttons/YellowButton";
import Link from "next/link";
import GreenLabel from "../labels/Geenlabel";
import RedLabel from "../labels/RedLabel";
import Api from "../../api/api";
import Select from "react-select/base";
import {API} from "../../libs/api";

export default function Projects({ project, owner }) {
    const [user, setUser] = useState();

    useEffect(async () => {
        const {data} = await API.getUser(project?.Author)
        setUser(data);
    }, []);

    console.log(project)


    return (
        <Wrapper>
            <Header>
                <Profile>
                    <NameContainer>
                        <Link href={`/profile/${project?.Author?.id || user?.id }`} >
                            <a>
                                <AvatarContainer>
                                    <img src='/images/avatar-2.png'
                                         alt='Нет аватара'
                                    />
                                </AvatarContainer>
                            </a>
                        </Link>
                    </NameContainer>
                    <NameContainer>
                        <Link href={`/profile/${project?.Author?.id || user?.id}`} >
                            <a>
                                {user?.username || project?.Author?.username || 'нет автора'}
                            </a>
                        </Link>
                    </NameContainer>
                    <Category>
                        { project?.category?.name ? project?.category?.name : 'Разное' }
                    </Category>
                </Profile>
                {owner === undefined ?
                    <Link href={`/projects/${project.id}`}>
                        <a>
                            <YellowButton text='Открыть проект'/>
                        </a>
                    </Link>
                    : owner === true ?
                        <RedLabel text='Понравилось'/>
                        :
                        <GreenLabel text='Моя'/>

                }

            </Header>
            <Body>
                <div>
                    <h2>{project?.name || 'Какая-нибудь интересная идея'}</h2>
                </div>
                <Description>
                    <p>
                        {project?.description || 'If you have small description you should write here.'}
                    </p>
                </Description>
            </Body>
            <Footer>
                <UsersCount>
                    <img
                        src='/images/user.png'
                        alt='нет картинки'
                    />
                </UsersCount>

                {project?.users || 5}
                <Message>
                    <img
                        src='/images/message-circle.png'
                        alt='нет картинки'
                    />
                </Message>
                {project?.projectComments?.length || 0}
            </Footer>
        </Wrapper>
    )
};

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    background-color: #FFF4CC;
    color: #18191F;
    border: 2px solid #18191F;
    padding: 24px;
    height: auto;
    width: 820px;
    border-radius: 12px;
    text-align: left;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    box-shadow: 0px 6px 0px #18191F;
  margin-bottom: 30px;
`;

const Header = styled.div`
    display: flex;
  align-items: center;
  margin-bottom: 20px;
  justify-content: space-between;
`;

const AvatarContainer = styled.div`
    width: 32px;
    height: 32px;
  margin-right: 20px;
`;

const NameContainer = styled.div`
    align-self: center;
    margin-left: 12px;
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
  margin-left: 30px;
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
  align-items: center;
`;

const Category = styled.div`
  display: flex;
  margin-right: auto;
  padding-left: 20px;
  font-weight: 800;
  color: #484848;
  margin-top: 10px;
  font-size: 18px;
`;
