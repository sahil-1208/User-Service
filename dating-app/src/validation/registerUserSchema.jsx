
import * as yup from 'yup';

export const registerUserSchema = yup.object().shape({
  name: yup.string().required('Name is required'),
  email: yup.string().email('Invalid email address').required('Email is required'),
  contactNo: yup.string().matches(/^\d{10}$/, 'Contact number must be 10 digits').required('Contact number is required'),
  gender: yup.string().required('Gender is required'),
  password: yup.string().min(4).max(12).matches(/^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])/, 'Password must have at least one uppercase letter, one digit, and one special character').required('Password is required'),
  dob: yup.date("Please select your date").required('Date of Birth is required'),
  location: yup.string().required('Allow Location!!!'),
});

