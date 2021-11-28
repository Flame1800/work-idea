import React from "react";
import styled from "styled-components"

export default function RedLabel({text}) {
    return (
        <Wrapper>
            <p>
                {text ? text : 'Понравилось'}
            </p>
        </Wrapper>
    )
}

const Wrapper = styled.div`
display: flex;
  align-items: center;
background-color: #F95A2B;
color: white;
padding: 12px;
height: 20px;
width: auto;
border-radius: 26px;
text-align: center;
font-weight: bold;
text-decoration: none;
align-self: center;
`;