import { useState } from "react";

export default function ProjectCard({ project, onClick, onEdit, onDelete }) {
  const [confirmOpen, setConfirmOpen] = useState(false);

  return (
    <>
      {/* Card */}
      <div
        className="group cursor-pointer rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition
                   hover:shadow-lg hover:-translate-y-1 hover:border-slate-300"
        onClick={onClick}
      >
        <div className="space-y-2">
          <h3 className="text-lg font-semibold text-slate-800 group-hover:text-blue-600 transition">
            {project.title}
          </h3>

          <p className="text-sm text-slate-500 line-clamp-3">
            {project.description || "No description provided."}
          </p>
        </div>

        <div
          className="mt-4 flex justify-end gap-3"
          onClick={(e) => e.stopPropagation()}
        >
          <button
            onClick={onEdit}
            className="rounded-lg px-4 py-1.5 text-sm font-medium text-slate-600
                       hover:bg-slate-100 transition"
          >
            Edit
          </button>

          <button
            onClick={() => setConfirmOpen(true)}
            className="rounded-lg px-4 py-1.5 text-sm font-medium text-red-600
                       hover:bg-red-50 transition"
          >
            Delete
          </button>
        </div>
      </div>

      {/* Confirmation Modal */}
      {confirmOpen && (
        <div
          className="fixed inset-0 z-[9999] flex items-center justify-center
                     bg-black/40 backdrop-blur-sm px-4"
          onClick={() => setConfirmOpen(false)}
        >
          <div
            className="w-full max-w-sm rounded-2xl bg-white p-6 shadow-2xl"
            onClick={(e) => e.stopPropagation()}
          >
            <h3 className="text-lg font-semibold text-slate-800">
              Delete project?
            </h3>

            <p className="mt-2 text-sm text-slate-500">
              This action cannot be undone. Are you sure you want to delete
              <span className="font-medium text-slate-700">
                {" "}
                “{project.title}”
              </span>
              ?
            </p>

            <div className="mt-6 flex justify-end gap-3">
              <button
                onClick={() => setConfirmOpen(false)}
                className="rounded-lg px-4 py-2 text-sm font-medium
                           text-slate-600 hover:bg-slate-100 transition"
              >
                Cancel
              </button>

              <button
                onClick={() => {
                  setConfirmOpen(false);
                  onDelete(); // 🔥 delete ONLY after confirmation
                }}
                className="rounded-lg bg-red-600 px-4 py-2 text-sm font-medium
                           text-white hover:bg-red-700 transition"
              >
                Delete
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
