import {ChangeDetectorRef, Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AdminControllerService} from "../../../../../../services/services/admin-controller.service";
import {UserControllerService} from "../../../../../../services/services/user-controller.service";
import {PageResponseBakeryResponse} from "../../../../../../services/models/page-response-bakery-response";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-list-bakery',
  imports: [
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './list-bakery.component.html',
  standalone: true,
  styleUrl: './list-bakery.component.scss'
})
export class ListBakeryComponent implements OnInit{

  constructor(
    private adminService: AdminControllerService,
    private userService: UserControllerService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}


  page= 0;
  size = 10;
  bakerys: PageResponseBakeryResponse = {};
  test = this.bakerys.content
  pages: any =[]


  ngOnInit(): void {
    this.findAllUser()
    this.cdr.detectChanges()
  }



  // TODO cambaire funzione get data con bakery


  findAllUser(){
    if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()
      if (toc) {
        this.adminService.getAllBakery({
          page: this.page,
          size: this.size
        }).subscribe({
          next: (res) => {
            this.bakerys = res;
            this.pages = Array(this.bakerys.totalPages).fill(0).map((x,i) =>i )
          },
          error: (err) => {
            console.error("Fetch request Fallito: " + err)
          }
        })
      }
    }
  }

  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token");
    }
    return null
  }


  toSearch: string = "";
  isEmpty = true;

  searchRequestByName(){
    this.bakerys = {}
    this.userService.searchUserByName({
      name: this.toSearch
    }).subscribe({
      next:(res) => {
        this.bakerys = res
        this.isEmpty = false;
      },
      error:() => {
        this.isEmpty = true;
        console.error("Errore nella ricerca tramite nome")
      }
    })
  }


  goToPreviusPage(){
    this.page --;
    this.findAllUser()
  }

  goToPage(page: number){
    this.page = page;
    this.findAllUser()
  }

  goToNextPage(){
    this.page ++
    this.findAllUser()
  }

  isLastPage(){
    return this.page === this.bakerys.totalPages as number - 1;
  }




}
