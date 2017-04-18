import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';

@Component({
    selector: 'jhi-menu',
    templateUrl: './menu.component.html',
    styleUrls: [
        'menu.scss'
    ]
})
export class MenuComponent implements OnInit {

    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        private languageService: JhiLanguageService,
        private router: Router
    ) {
        this.isNavbarCollapsed = true;
        this.languageService.addLocation('home');
    }

    ngOnInit() {
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }
    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

}
