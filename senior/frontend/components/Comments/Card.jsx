import styled from 'styled-components'
import React from "react";
import Link from "next/link";


export default function CommentCard({comment}) {

    console.log(comment.users_permissions_user)

     if (!comment.users_permissions_user) return  null

    return (
        <Wrapper>
            <Header>
                <Flex>
                    <Link href={`/profile/${comment?.users_permissions_user?.id || 0}`} >
                        <a>
                            <AvatarContainer>
                                <img src='/images/avatar-2.png'
                                     alt='Нет аватара'
                                />
                            </AvatarContainer>
                        </a>
                    </Link>
                    <NameContainer>
                        <Link href={`/profile/${comment?.users_permissions_user?.id || 0}`} >
                            <a>
                                {comment?.users_permissions_user?.username || 'Lamborci Mona'}
                            </a>
                        </Link>

                        <Speciality>
                            {comment?.comment || 'None'}
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
    width: 650px;
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
font-size: 18px;
  margin-top: 10px;
line-height: 18px;
color: #454A57;
`;
