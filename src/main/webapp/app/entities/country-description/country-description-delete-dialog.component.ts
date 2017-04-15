import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CountryDescription } from './country-description.model';
import { CountryDescriptionPopupService } from './country-description-popup.service';
import { CountryDescriptionService } from './country-description.service';

@Component({
    selector: 'jhi-country-description-delete-dialog',
    templateUrl: './country-description-delete-dialog.component.html'
})
export class CountryDescriptionDeleteDialogComponent {

    countryDescription: CountryDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private countryDescriptionService: CountryDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['countryDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.countryDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'countryDescriptionListModification',
                content: 'Deleted an countryDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-country-description-delete-popup',
    template: ''
})
export class CountryDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryDescriptionPopupService: CountryDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.countryDescriptionPopupService
                .open(CountryDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
