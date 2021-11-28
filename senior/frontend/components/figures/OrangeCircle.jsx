import React from "react";
import styled from "styled-components"

export default function OrangeCircle({text}) {
    return (
        <Wrapper>
            <p>
                {text ? text : null}
            </p>
        </Wrapper>
    )
}

const Wrapper = styled.div`
width: 24px;
height: 24px;
background: #FFBD12;
border: 2px solid #18191F;
border-radius: 50%;
`;