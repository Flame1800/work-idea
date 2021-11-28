import styled from 'styled-components';
import React, {useEffect, useState} from "react";
import Link from "next/link";

export default function UserInfo({specialist}) {

    return (
        <Wrapper>
            <Header>
                <Flex>
                    <Link href={`/profile/${specialist?.id || 0}`} >
                        <a>
                            <AvatarContainer>
                                <img src='/images/avatar-2.png'
                                     alt='Нет аватара'
                                />
                            </AvatarContainer>
                        </a>
                    </Link>
                    <NameContainer>
                        <Link href={`/profile/${specialist?.id || 0}`} >
                            <a>
                                {specialist.username || 'Lamborci Mona'}
                            </a>
                        </Link>
                            <Speciality>
                        </Speciality>
                    </NameContainer>
                    <ProjectCounts>{specialist.count || 0}</ProjectCounts>
                    <Projects>Проектов</Projects>
                </Flex>
            </Header>
            <Body>
                <div>
                    <h2>Навыки</h2>
                </div>
                <Description>
                    <p>
                        {specialist.categories_of_specializations ?
                            specialist.categories_of_specializations
                                .map((spec, index, arr) => index < arr.length - 1 ? `${spec?.name}, ` : spec?.name) :
                            'no skills'
                        }
                    </p>
                </Description>
            </Body>
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
    height: auto;
    width: 820px;
    border-radius: 12px;
    text-align: left;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    box-shadow: 0px 6px 0px #18191F;
    margin-bottom: 45px;
`;

const Header = styled.div`
    display: flex;
    justify-content: space-between;
`;

const Flex = styled.div`
    display: flex;
`;


const AvatarContainer = styled.div`
    width: 32px;
    height: 32px;
    margin-right: 16px;
`;

const NameContainer = styled.div`
    align-self: center;
    margin-left: 12px;
`;

const Speciality = styled.div`
    font-style: normal;
    font-weight: 500;
    font-size: 13px;
    line-height: 18px;
    color: #454A57;
`;

const Projects = styled.div`
    font-style: normal;
    font-weight: 500;
    font-size: 18px;
    line-height: 20px;
    margin-top: 12px;
`;

const ProjectCounts = styled.div`
    font-style: normal; 
    font-weight: 800; 
    font-size: 36px; 
    line-height: 40px;
    color: #454A57;
    margin-left: 36px;
    margin-right: 19px;
`;

const Body = styled.div`
    display: flex;
    flex-direction: column;
    padding-bottom: 60px;
`;

const Description = styled.div`
    color: #454A57;
    margin-top: -25px;
`;