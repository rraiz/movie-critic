import Card from "./Card.jsx";

export default function ScrollCard({members}) {

  const sortedMembers = [...members].sort((a, b) => a.ordering - b.ordering);

  return (
    <div className="col-span-3 mt-10">
    <a className="ml-5 mt-3 font-bold text-[25px] ">Series Cast</a>
    <main className="flex overflow-x-auto  space-x-4 p-4 items-stretch">
      {sortedMembers.map((member) => (
          <Card
            member={member} 
          />
      ))}
    </main>
    </div>
  );
}
