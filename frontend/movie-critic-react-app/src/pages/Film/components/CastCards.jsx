import Card from "./Card.jsx";

export default function CastCards({ members, type }) {
  const sortedMembers = [...members].sort((a, b) => a.ordering - b.ordering);

  return (
    <div className="col-span-3 mt-10">
      <a className="ml-5 mt-3 font-bold text-[25px]">
        {type === "movie" ? "Cast" : "Series Cast"}
      </a>
      <main className="flex overflow-x-auto space-x-4 p-4 items-stretch scrollbar">
        {sortedMembers.map((member) => (
          <Card key={member.id} member={member} />
        ))}
      </main>
    </div>
  );
}
