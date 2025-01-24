import { HttpInterceptorFn } from '@angular/common/http';

export const httpInterceptorInterceptor: HttpInterceptorFn = (req, next) => {

  let token;

  if(typeof window !== 'undefined') {
    token = localStorage.getItem("token")
  }

  if(token){
    let authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }
  return next(req);
};


