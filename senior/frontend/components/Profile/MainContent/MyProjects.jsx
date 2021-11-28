import styled from 'styled-components';
import React, {useEffect, useState} from "react";
import Api from "../../../api/api";
import Projects from "../../Cards/Projects";

export default function MyProjects({user}) {
    const [projects, setProjects] = useState([]);

    useEffect(async () => {
        const response = await Api.getParticipants();

        const project = response.data.filter( elem => elem?.project?.Author === user.id || elem?.user?.id === user.id);
        setProjects(project.map( data => <Projects project={data.project} owner={data?.user?.id === user.id}/>))
    }, []);

    return (
        <Wrapper>
            {projects}
        </Wrapper>
    )
}

const Wrapper = styled.div`
  max-width: 1300px;
  margin: 30px auto;
  min-height: 500px;
`;