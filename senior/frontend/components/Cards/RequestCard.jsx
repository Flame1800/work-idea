import styled from 'styled-components'
import React from "react";
import BlackButton from "../buttons/BlackButton";
import WhiteButton from "../buttons/WhiteButton";
import {API} from "../../libs/api";


export default function User({participant}) {

    const [accept, setAccept] = React.useState(null)

    const sendRequest = async () => {
        await API.acceptRequest(participant.id)

        await API.addParticipant({
            project: participant.project.id,
            user: participant?.users_permissions_user?.id,
            specialization: participant?.specialization?.id
        })

        setAccept('yes')
    }

    const closeRequest = async () => {
        await API.closeRequest(participant.id)
        setAccept('no')
    }

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
                        {participant?.users_permissions_user?.username || 'Lamborci Mona'}
                        <Speciality>
                            {participant?.specialization?.name || 'None'}
                        </Speciality>
                    </NameContainer>
                </Flex>
            </Header>
            {accept === 'no' && <>Запрос отклонен</>}
            {accept === 'yes' && <>Участник принят в команду</>}
            {accept === null &&
                <>
                    <div  onClick={() => sendRequest()}>
                        <BlackButton

                            text="Взять в команду"
                        />
                    </div>
                    <Btn  onClick={() => closeRequest()}>
                        <WhiteButton text="Отклонить запрос" />
                    </Btn>
                </>
                }

        </Wrapper>
    )
};

const Wrapper = styled.div`
    display: flex;
  margin-right: 20px;
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
  margin-bottom: 30px;
`;

const Flex = styled.div`
    display: flex;
`;
const Btn = styled.div`
  margin-top: 10px;
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
