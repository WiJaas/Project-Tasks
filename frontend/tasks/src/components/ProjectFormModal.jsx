// src/components/ProjectFormModal.jsx
import { useEffect, useState } from "react";
import { createPortal } from "react-dom";

export default function ProjectFormModal({ project, onClose, onSubmit }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    if (project) {
      setTitle(project.title || "");
      setDescription(project.description || "");
    } else {
      setTitle("");
      setDescription("");
    }
  }, [project]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ title, description });
  };

  return createPortal(
    <div
      className="fixed inset-0 z-[9999] flex items-center justify-center
                 bg-black/40 backdrop-blur-sm px-4"
    >
      <div className="w-full max-w-md rounded-2xl bg-white p-8 shadow-2xl">
        <h3 className="mb-6 text-xl font-semibold text-slate-800">
          {project ? "Edit Project" : "Create Project"}
        </h3>

        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            required
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full rounded-lg border border-slate-300 px-4 py-2.5
                       text-sm focus:outline-none focus:ring-2
                       focus:ring-blue-500 focus:border-blue-500"
          />

          <textarea
            placeholder="Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={4}
            className="w-full resize-none rounded-lg border border-slate-300
                       px-4 py-2.5 text-sm focus:outline-none
                       focus:ring-2 focus:ring-blue-500
                       focus:border-blue-500"
          />

          <div className="mt-6 flex justify-end gap-3">
            <button
              type="button"
              onClick={onClose}
              className="rounded-lg px-4 py-2 text-sm font-medium
                         text-slate-600 hover:bg-slate-100 transition"
            >
              Cancel
            </button>

            <button
              type="submit"
              className="rounded-lg bg-blue-600 px-5 py-2
                         text-sm font-medium text-white
                         hover:bg-blue-700 transition"
            >
              {project ? "Update" : "Create"}
            </button>
          </div>
        </form>
      </div>
    </div>,
    document.body
  );
}
