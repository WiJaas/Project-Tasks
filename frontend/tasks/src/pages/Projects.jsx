// src/pages/Projects.jsx
import { useState } from "react";
import { useProjects } from "../hooks/useProjects";
import ProjectCard from "../components/ProjectCard";
import ProjectFormModal from "../components/ProjectFormModal";
import { useNavigate } from "react-router-dom";

export default function Projects() {
  const navigate = useNavigate();
  const { projects, loading, error, addProject, editProject, removeProject } =
    useProjects();

  const [showModal, setShowModal] = useState(false);
  const [selectedProject, setSelectedProject] = useState(null);

  const openCreate = () => {
    setSelectedProject(null);
    setShowModal(true);
  };

  const openEdit = (project) => {
    setSelectedProject(project);
    setShowModal(true);
  };

  const handleSubmit = async (data) => {
    if (selectedProject) {
      await editProject(selectedProject.id, data);
    } else {
      await addProject(data);
    }
    setShowModal(false);
  };

  if (loading)
    return (
      <div className="flex items-center justify-center py-20 text-slate-500">
        Loading projects...
      </div>
    );

  if (error)
    return (
      <div className="flex items-center justify-center py-20 text-red-600">
        {error}
      </div>
    );

  return (
    <div className="min-h-screen bg-slate-50 px-6 py-8">
      {/* Header */}
      <div className="mx-auto max-w-7xl">
        <div className="mb-8 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <h2 className="text-2xl font-semibold text-slate-800">
            My Projects
          </h2>

          <button
            onClick={openCreate}
            className="inline-flex items-center justify-center rounded-lg
                       bg-blue-600 px-5 py-2.5 text-sm font-medium text-white
                       shadow hover:bg-blue-700 transition"
          >
            + Create Project
          </button>
        </div>

        {/* Empty state */}
        {projects.length === 0 && (
          <div className="rounded-xl border border-dashed border-slate-300
                          bg-white py-16 text-center text-slate-500">
            No projects yet.
          </div>
        )}

        {/* Grid */}
        <div
          className="grid gap-6
                     sm:grid-cols-2
                     lg:grid-cols-3
                     xl:grid-cols-4"
        >
          {projects.map((project) => (
            <ProjectCard
              key={project.id}
              project={project}
              onClick={() => navigate(`/projects/${project.id}`)}
              onEdit={() => openEdit(project)}
              onDelete={() => removeProject(project.id)}
            />
          ))}
        </div>
      </div>

      {/* Modal */}
      {showModal && (
        <ProjectFormModal
          project={selectedProject}
          onClose={() => setShowModal(false)}
          onSubmit={handleSubmit}
        />
      )}
    </div>
  );
}
