import styled from 'styled-components'
import React from "react";
import Image from "next/image";

export default function Idea() {
    return (
        <Wrapper>
            <Header>
                <AvatarContainer>
                    <Image src='/images/avatar-2.png'
                           width={144}
                           height={144}
                           alt='Нет аватара'
                    />
                </AvatarContainer>

            </Header>
            <Body>
                <div>
                    <h2>Какая-нибудь интересная идея</h2>
                </div>
                <div>
                    <p>
                        If you have small description you should write here.
                    </p>
                </div>
            </Body>
            <Footer>
                <FlexRow>
                    <UserContainer>
                        <Image
                            src='/images/user.png'
                            width={60}
                            height={60}
                            alt='нет картинки'
                        />
                    </UserContainer>
                    <div>
                        <p>требуются участники</p>
                    </div>

                </FlexRow>
                <div>
                    <UserContainer>
                        <Image
                            src='/images/share.png'
                            width={60}
                            height={60}
                            alt='нет картинки'
                        />
                    </UserContainer>

                </div>
            </Footer>
        </Wrapper>
    )
};

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    background-color: #FFBD12;
    color: white;
    border: 2px solid #18191F;
    padding: 24px;
    height: auto;
    width: 320px;
    border-radius: 12px;
    text-align: center;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    box-shadow: 0px 6px 0px #18191F;
`;

const Header = styled.div`
    display: flex;
`;

const AvatarContainer = styled.div`
    width: 32px;
    height: 32px;
`;

const UserContainer = styled.div`
    width: 24px;
    height: 24px;
`;

const Body = styled.div`
display: flex;
flex-direction: column;
`;

const Footer = styled.div`
    display: flex;
    justify-content: space-between;
`;

const FlexRow = styled.div`
    display: flex;
`;