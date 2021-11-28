import styled from 'styled-components'
import React from "react";
import TextInput from "./TextInput";
import CommentCard from "./Card";
import {API} from "../../libs/api";
import {useRouter} from "next/router";
import {useAppDispatch, useAppSelector} from "../../redux/hooks";
import {setComments} from "../../redux/slices/comment";

export default function CommentsBlock({project, idea}) {
    const dispatch = useAppDispatch()
    const comments = useAppSelector(state => state.comments)

    const name = idea ? "idea" : "project"

    React.useEffect(async () => {
        const {data} = idea ?  await API.getIdeaComments() : await API.getProjectComments()
        dispatch(setComments(data.filter(comment => comment[name]?.id === project.id)))
    }, [])


    return (
        <Wrapper>
            <Text>Обсуждения</Text>
            <TextInput project={project} />
            <Comments>
                {comments.map(comment => {
                    return <CommentCard comment={comment} />
                })}
            </Comments>
        </Wrapper>
    )
};

const Text = styled.div`
  font-size: 27px;
  font-weight: 800;
  margin-bottom: 20px;
  margin-top: 50px;
`;

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
`;

const Comments = styled.div`
    display: flex;
    flex-direction: column;
  margin-top: 50px;
`;
