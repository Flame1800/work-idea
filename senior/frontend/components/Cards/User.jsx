import styled from 'styled-components'
import React from "react";

export default function User({user}) {
    return (
        <Wrapper>
            <Header>
                <Flex>
                    <AvatarContainer>
                        <img src='/images/avatar-2.png'
                             alt='Нет аватара'
                        />
                    </AvatarContainer>
                    <NameContainer>
                        {user?.user?.username || 'Lamborci Mona'}
                        <Speciality>
                            {user?.specialization?.name || 'None'}
                        </Speciality>
                    </NameContainer>
                </Flex>
            </Header>

        </Wrapper>
    )
};

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    background-color: white;
    color: #18191F;
    border: 2px solid #18191F;
    padding: 24px;
    height: auto;
    width: 320px;
    border-radius: 12px;
    text-align: left;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
  margin-bottom: 20px;
`;

const Header = styled.div`
    display: flex;
    justify-content: space-between;
`;

const Flex = styled.div`
    display: flex;
`;

const AvatarContainer = styled.div`
    display:flex;
    width: 60px;
    height: 60px;
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
