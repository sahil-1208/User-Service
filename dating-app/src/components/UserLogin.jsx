import React from "react";
import { useForm } from "react-hook-form";

function UserLogin() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log(data);
    // Perform your authentication logic here
  };

  return (
    <main className='flex justify-center h-screen items-center bg-gradient-to-r  from-red-50 to-blue-700'>
    <form className="p-5 space-y-4 bg-gradient-to-r from-blue-200 to-violet-300 font-semibold border-2 border-violet-500 text-black grid grid-cols-1 w-1/4 h-3/5 rounded-lg hover:shadow-2xl shadow-red-500"  onSubmit={handleSubmit(onSubmit)}>
      <h2 className="font-extrabold text-2xl">Login!! To Your Account</h2>
 
      <label htmlFor="email" className="ml-3 text-xl" >Email:
      </label>
      <input className="p-3 ml-2 border border-violet-500 rounded-xl pl-2"
        type="email"
        id="email"
        placeholder="Enter your email"
        {...register("email", { required: "Email is required" })}
      />
      
      {errors.email && <span className="text-red-400">{errors.email.message}</span>}

      <label htmlFor="password" className="ml-2 text-xl">Password:
       </label>
       <input className=" p-3 ml-2 border border-violet-500 rounded-xl pl-2"
        type="password"
        id="password"
        placeholder="Enter your password"        
        {...register("password", { required: "Password is required" })}
      />
     
      {errors.password && <span className="text-red-400">{errors.password.message}</span>}

      <button type="submit" className=" bg-blue-600 hover:bg-blue-400 active:text-gray-300 p-3 rounded-lg" >Login</button>

      <h1>Don't have an Account <a href={'/signup'} className="text-blue-500 hover:underline underline-offset-8 cursor-pointer active:text-blue-200"> Sign Up!</a></h1>
    </form>
    </main>
  );
}

export default UserLogin;
