import React from "react";
import styled from "styled-components"
import Checker from "../../checkers/Checker";
import LinkCont from "./LinkCont";
import {useAppSelector} from "../../../redux/hooks";

export default function ProfileBar({entity}) {
    const user = useAppSelector(state => state.user)

    return (
        <Cont>
        <Wrapper>
            <PersonCont>
                <Person>
                    <img src="/images/big-avatar.png" />
                    <NameCont>
                        <p>
                            <Name>{entity?.username}</Name>
                            <Desc>{entity?.email}</Desc>
                        </p>
                        <Checker />
                    </NameCont>
                </Person>
                <ProjectsCount>
                    <span>{0}</span>Проектов на счету
                </ProjectsCount>
            </PersonCont>
            {entity.id === user?.id && <LinkCont />}
        </Wrapper>
        </Cont>
)
}

const Cont = styled.div`

  display: flex;
  flex-direction: column;
`;

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 80px;
  margin-bottom: 80px;
`;

const PersonCont = styled.div`
  display: flex;
  flex-direction: column;
`

const Person = styled.div`
  display: flex;
`

const NameCont = styled.div`
  margin-left: 30px;
  height: 180px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`

const  Name = styled.div`
  font-size: 36px;
  font-weight: 800;
  margin-bottom: 10px;
`

const Desc = styled.div`
  font-size: 21px;
  font-weight: 500;
`

const ProjectsCount = styled.div`
  display: flex;
  align-items: center;
  font-weight: 500;
  font-size: 18px;
  line-height: 20px;
  text-align: center;
  color: #474A57;
  margin-left: 20px;
  margin-top: 20px;
  
  span {
    font-style: normal;
    font-weight: 800;
    font-size: 36px;
    line-height: 40px;
    margin-right: 20px;
    color: #747474;
  }
`