import React, {useState} from "react";
import styled from "styled-components"
import Nav from "./Nav";
import Blank from "./Blank";
import MyProjects from "./MyProjects";
import MyIdeas from "./MyIdeas";

export default function MainContent({entity}) {
    const [chose, setChose] = useState('Мои проекты');

    return (
        <Wrapper>
            <Nav chose={chose} setChose={setChose}/>
            <Container>
                {chose === 'Мои проекты' ? <MyProjects user={entity}/> : null }
                {chose === 'Мои идеи' ? <MyIdeas user={entity}/> : null }
                {chose === 'Анкета' ? <Blank entity={entity} /> : null }

            </Container>
        </Wrapper>
    )
}

const Wrapper = styled.div`
  width: 100%;
  margin: 0 auto;
`;

const Container = styled.div`
  width: 100%;
  border-left: black 1px solid;
  border-right: black 1px solid;
  border-bottom: black 1px solid;
  padding-top: 10px;
`;



