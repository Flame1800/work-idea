import styled from 'styled-components'
import React from "react";
import MainLayout from "../components/MainLayout";
import OrangeButton from "../components/buttons/OrangeButton";
import Link from "next/link";
import {API} from "../libs/api";
import {useAppDispatch} from "../redux/hooks";
import {setUser} from "../redux/slices/user";
import { useRouter } from 'next/router'

export default function Home() {
    const [login, setLogin] = React.useState('')
    const [email, setEmail] = React.useState('')
    const [password, setPassword] = React.useState('')

    const router = useRouter()
    const dispatch = useAppDispatch()

    const sendForm = async () => {
        const form = {
            username: login,
            email,
            password
        }

        const {data} = await API.createUser(form)
        dispatch(setUser(data))
        router.push(`/profile/${data.user.id}`)
    }

    return (
        <MainLayout>
            <Form>
                <Title>Регистрация</Title>
                <SubTitle>Регистрация</SubTitle>
                <Input
                    placeholder="Логин" required={true}
                    onChange={({target}) => setLogin(target.value)}
                    value={login}
                />
                <Input
                    placeholder="Email"  type="mail" required={true}
                    onChange={({target}) => setEmail(target.value)}
                    value={email}
                />
                <Input
                    placeholder="Пароль" type="password" required={true}
                    onChange={({target}) => setPassword(target.value)}
                    value={password}
                />
                <a onClick={() => sendForm()}>
                    <OrangeButton text="Зарегистриоваться" />
                </a>
                <Info>
                    <div>
                        Уже зарегистрированны?
                    </div>
                    <Link href="/login" >
                        <a>Войдите</a>
                    </Link>
                </Info>
            </Form>
        </MainLayout>
    )
}

const Form = styled.div`
  display: flex;
  flex-direction: column;
  width: 375px;
  padding: 20px;
  margin: 100px auto;
  
  a {
    margin: 0 auto;
  }
`

const Title = styled.div`
  font-size: 36px;
  font-weight: 800;
  margin-bottom: 10px;
`
const SubTitle = styled.div`
  font-size: 17px;
  color: #474A57;
  margin-bottom: 20px;
`

const Input = styled.input`
  border: 2px solid #18191F;
  border-radius: 15px;
  font-size: 21px;
  height: 60px;
  color: #000;
  padding-left: 20px;
  margin-bottom: 20px;
`

const Info = styled.div`
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
  font-size: 18px;
  font-weight: 500;
  
  a {
    color: #F95A2C;
  }
`