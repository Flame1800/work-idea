import styled from 'styled-components';
import React, {useEffect, useState} from "react";
import Api from "../../../api/api";
import Ideas from "../../Cards/Ideas";

export default function MyIdeas({user}) {
    const [projects, setProjects] = useState([]);

    useEffect(async () => {
        const response = await Api.getUser(user.id);
        console.log(response.data.likedIdeas)
        setProjects(response.data.likedIdeas?.map(elem => <Ideas idea={elem}/>))
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