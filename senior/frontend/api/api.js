import axios from "axios";

const instanceAxios = axios.create({
    withCredentials: false,
    baseURL: 'http://193.168.48.37:1337/',
});


const Api = {
    createProject(data) {
        return instanceAxios.post('projects', data)
    },
    getProject() {
        return instanceAxios.get('projects')
    },
    deleteProject(id) {
        return instanceAxios.delete(`projects/${id}`)
    },
    getIdea() {
        return instanceAxios.get('ideas')
    },
    createIdea(data) {
        return instanceAxios.post('ideas', data)
    },
    getUser(id) {
        return instanceAxios.get(`users/${id}`, )
    },
    putUser(data, id) {
        return instanceAxios.put('users/'+id, data)
    },
    createParticipants(project, user, specialization) {
        return instanceAxios.post(`project-participants`, { project, specialization, participants: user})
    },
    getParticipants() {
        return instanceAxios.get(`project-participants`)
    },
    getCategoriesofSpecializations() {
        return instanceAxios.get('categories-of-specializations')
    }

};

export default Api;