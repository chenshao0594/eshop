import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CountryDescription } from './country-description.model';
import { CountryDescriptionPopupService } from './country-description-popup.service';
import { CountryDescriptionService } from './country-description.service';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-country-description-dialog',
    templateUrl: './country-description-dialog.component.html'
})
export class CountryDescriptionDialogComponent implements OnInit {

    countryDescription: CountryDescription;
    authorities: any[];
    isSaving: boolean;

    countries: Country[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private countryDescriptionService: CountryDescriptionService,
        private countryService: CountryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['countryDescription']);
        this.countryDescription = new CountryDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.countryDescription.id !== undefined) {
            this.countryDescriptionService.update(this.countryDescription)
                .subscribe((res: CountryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.countryDescriptionService.create(this.countryDescription)
                .subscribe((res: CountryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CountryDescription) {
        this.eventManager.broadcast({ name: 'countryDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.countryDescription = result;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-country-description-popup',
    template: ''
})
export class CountryDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryDescriptionPopupService: CountryDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.countryDescriptionPopupService
                    .open(CountryDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.countryDescriptionPopupService
                    .open(CountryDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
