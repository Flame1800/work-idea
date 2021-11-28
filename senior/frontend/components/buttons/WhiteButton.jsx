import styled from "styled-components";
import React from "react";


export default function WhiteButton({text}) {
    return (
        <Wrapper>{text}</Wrapper>
    );
}

const Wrapper = styled.button`
    display: flex;
    background-color: white;
    color: #18191F;
    border: 2px solid #18191F;
    padding: 10px 16px;
    height: auto;
    width: auto;
    border-radius: 12px;
    text-align: center;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    cursor: pointer;

`;