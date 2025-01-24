import {ChangeDetectorRef, Component, HostListener, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {
  faArrowRightFromBracket,
  faCartShopping,
  faChartLine,
  faMagnifyingGlass,
  faSliders, faTriangleExclamation, faUser
} from "@fortawesome/free-solid-svg-icons";
import {isPlatformBrowser, NgClass, NgIf} from "@angular/common";
import {AuthenticationService} from "../../../services/services/authentication.service";
import {UserControllerService} from "../../../services/services/user-controller.service";
import {isAdmin} from "../../../services/fn/user-controller/is-admin";



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
  protected readonly faSliders = faSliders;
  protected readonly faChartLine = faChartLine;
  protected readonly faCartShopping = faCartShopping;
  protected readonly faUser = faUser;
  protected readonly faMagnifyingGlass = faMagnifyingGlass;

  isLogged: boolean = false;
  userData:any;
  isDropdownOpen = false;
  isAdmin: boolean = false;


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


  controllo(){
    this.userService.isAdmin().subscribe({
      next:(res) => {
        this.isAdmin = res
      },
      error:() => {
        console.error("fallito")
      }
    })
  }

  fetchMod(){
    this.userService.getCurrentUser().subscribe({
      next: (data) => {
        this.userData = data;
      },
      error: (err) =>{
        console.log("errore: " + err);
      }
    })
  }

  checkLoginStatus(): void{
    if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()

      if(toc) {
        this.authService.check({
          token: toc
        }).subscribe({
          next: (res) => {
            this.isLogged = res;
            this.fetchMod()
            this.controllo()
            this.cdr.detectChanges();

          },
          error: (err) => {
            console.error("Errore nel verificare se sei loggato: " + err)
          }
        })
      }
    }
  }

  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token")
    }
    return null
  }


  logout():void {
    if(typeof localStorage.getItem('token') !== 'undefined'&& typeof window !=='undefined'){
      localStorage.removeItem("token")
      window.location.reload()
    }
  }


  goToUserPage() {
    this.router.navigate(['user']);
  }

  goToAdminPage(){
    this.router.navigate(['admin'])
  }

  protected readonly faTriangleExclamation = faTriangleExclamation;
}
