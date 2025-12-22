// src/api/apiService.js
import api from "./axiosClient";

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
    console.log(localStorage.getItem("token"));

  }
  return config;
});

// // ---------------- Projects ----------------
// export const getProjects = () => api.get("/projects");

// export const getProject = (projectId) => api.get(`/projects/${projectId}`);

// export const createProject = (projectData) =>
//   api.post("/projects", projectData);

// // ---------------- Tasks ----------------
// export const getTasks = (projectId) =>
//   api.get(`/projects/${projectId}/tasks`);

// export const createTask = (projectId, taskData) =>
//   api.post(`/projects/${projectId}/tasks`, taskData);

// export const completeTask = (projectId, taskId) =>
//   api.put(`/projects/${projectId}/tasks/${taskId}/complete`);

// export const deleteTask = (projectId, taskId) =>
//   api.delete(`/projects/${projectId}/tasks/${taskId}`);


// export default api;
