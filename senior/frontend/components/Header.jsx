import styled from "styled-components";
import React from "react";
import Link from "next/link";
import {useAppSelector} from "../redux/hooks";
import WhiteButton from "./buttons/WhiteButton";
import BlackButton from "./buttons/BlackButton";

export default function Header() {
    const user = useAppSelector(state => state.user)

    return (
        <Wrapper>
            <Left>
                <Link href="/" >
                    <a>
                        <Logo><span>Idea</span>Work</Logo>
                    </a>
                </Link>
                {user &&              <Nav>
                    <Link href="/projects">
                        <NavLink>Проекты</NavLink>
                    </Link>
                    <Link href="/ideas">
                        <NavLink>Идеи</NavLink>
                    </Link>
                    <Link href="/specialists">
                        <NavLink>Специалисты</NavLink>
                    </Link>
                </Nav>}
            </Left>
            {user.username !== null ? <Link href={`/profile/${user.id}`}><a><Profile src='/images/avatar-2.png' /></a></Link> :
                <Btns>
                    <Link href="/register" >
                        <a>
                            <WhiteButton text="Регистрация" />

                        </a>
                    </Link>
                    <Link href={`/login/`} >
                        <a>
                            <BlackButton text="Вход" />
                        </a>
                    </Link>
            </Btns>}
        </Wrapper>
    );
}

const Wrapper = styled.div`
  width: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Btns = styled.div`
  display: flex;
  button {
    margin-left: 10px;
  }
`

const Logo = styled.div`
  font-size: 34px;
  font-weight: 700;
  
  span {
    color: #F95A2B;
  }
`

const Left = styled.div`
  display: flex;
  align-items: center;
`

const Nav = styled.div`
  display: flex;
  margin-left: 60px;
`

const NavLink = styled.a`
  margin-left: 50px;
  cursor: pointer;
  font-weight: 600;
  
  &:hover {
    color: #F95A2C;
  }
`

const Profile = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 50%;
`