import "../film.css";
import Person from "../../../../public/alt-person.png";

export default function Card({member}) {
  return (
    <div className="w-[150px] min-h-[200px] rounded-lg card-shadow bg-[#2828295e] flex flex-col flex-none">
      <div className="flex justify-center">
        <img
          className="h-[95px] w-[70px] rounded-full mt-2 card-shadow border border-[rgba(0,0,0,0.37)]"
          src={member.profilePath ? `https://image.tmdb.org/t/p/original/${member.profilePath}` : Person}
          alt="Person"
        />
      </div>
      <div className="px-[10px] py-[10px] text-center flex-grow">
        <p className="font-bold text-[15px] text-gray-200">{member.personName}</p>
        <p className="text-gray-400 text-sm whitespace-normal">{member.character}</p>
      </div>
    </div>
  );
}
