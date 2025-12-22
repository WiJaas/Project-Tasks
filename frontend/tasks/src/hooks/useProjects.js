// src/hooks/useProjects.js
import { useEffect, useState } from "react";
import * as projectService from "../services/projectService";

export const useProjects = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const loadProjects = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await projectService.getProjects();
      setProjects(data);
    } catch {
      setError("Failed to load projects");
    } finally {
      setLoading(false);
    }
  };

  const addProject = async (data) => {
    const newProject = await projectService.createProject(data);
    setProjects((prev) => [...prev, newProject]);
  };

  const editProject = async (id, data) => {
    const updated = await projectService.updateProject(id, data);
    setProjects((prev) =>
      prev.map((p) => (p.id === id ? updated : p))
    );
  };

  const removeProject = async (id) => {
    await projectService.deleteProject(id);
    setProjects((prev) => prev.filter((p) => p.id !== id));
  };

  useEffect(() => {
    loadProjects();
  }, []);

  return {
    projects,
    loading,
    error,
    addProject,
    editProject,
    removeProject,
  };
};
