import {ChangeDetectorRef, Component, HostListener, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {
  faArrowRightFromBracket,
  faCartShopping,
  faChartLine,
  faCircleQuestion, faMagnifyingGlass,
  faSliders, faUser
} from "@fortawesome/free-solid-svg-icons";
import {isPlatformBrowser, NgClass, NgIf} from "@angular/common";
import {AuthenticationService} from "../../../services/services/authentication.service";
import {UserControllerService} from "../../../services/services/user-controller.service";
import {data} from "autoprefixer";



@Component({
  selector: 'app-navbar',
  imports: [
    FaIconComponent,
    RouterLink,
    NgIf,
    NgClass
  ],
  templateUrl: './navbar.component.html',
  standalone: true,
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{


  constructor(
    private router:Router,
    private authService: AuthenticationService,
    private userService: UserControllerService,
    private cdr: ChangeDetectorRef,
  @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }


  protected readonly faArrowRightFromBracket = faArrowRightFromBracket;
  protected readonly faCircleQuestion = faCircleQuestion;
  protected readonly faSliders = faSliders;
  protected readonly faChartLine = faChartLine;
  protected readonly faCartShopping = faCartShopping;
  protected readonly faUser = faUser;
  protected readonly faMagnifyingGlass = faMagnifyingGlass;

  isLogged: boolean = false;
  userData:any;
  isDropdownOpen = false;


  toggleDropdown(){
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent){
    const target = event.target as HTMLElement;
    const clickedInside = target.closest('.relative');
    if(!clickedInside){
      this.isDropdownOpen = false;
    }
  }


  ngOnInit() {
    this.checkLoginStatus();
    this.cdr.detectChanges();
  }







  fetchMod(){
    this.userService.getCurrentUser().subscribe({
      next: (data) => {
        this.userData = data;
        console.log("UserData: "+ JSON.stringify(this.userData, null ,2));
        console.log("Data: "+ JSON.stringify(data, null ,2));
      },
      error: (err) =>{
        console.log("errore: " + err);
      }
    })
  }



  checkLoginStatus(): void{
    if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()
      this.authService.isTokenValid(toc)
        .subscribe((isValid) => {
          this.isLogged = isValid
          this.cdr.detectChanges();
          this.fetchMod()
        });
    }
  }




  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){}
    return localStorage.getItem("token")
  }


  logout():void {
    this.authService.logout();
    window.location.reload()
  }


  goToUserPage() {
    this.router.navigate(['user']);
  }




}
