import styled from "styled-components";
import React from "react";
import Link from "next/link";
import Header from "./Header";
import {useAppSelector} from "../redux/hooks";
import {useRouter} from "next/router";

export default function MainLayout({ children }) {
    return (
        <Wrapper>
            <Container>
                <Header />
                {children}
            </Container>
        </Wrapper>
    );
}

const Wrapper = styled.div`
  width: 100%;
  animation: mymove 1s;

  /* Стандартный синтаксис */
  @keyframes mymove {
    0% {
      opacity: 0;
    }
  }
`;

const Container = styled.div`
  margin: 0 120px;
`;