import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Zone } from './zone.model';
import { ZonePopupService } from './zone-popup.service';
import { ZoneService } from './zone.service';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-zone-dialog',
    templateUrl: './zone-dialog.component.html'
})
export class ZoneDialogComponent implements OnInit {

    zone: Zone;
    authorities: any[];
    isSaving: boolean;

    countries: Country[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private zoneService: ZoneService,
        private countryService: CountryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['zone']);
        this.zone = new Zone();
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
        if (this.zone.id !== undefined) {
            this.zoneService.update(this.zone)
                .subscribe((res: Zone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.zoneService.create(this.zone)
                .subscribe((res: Zone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Zone) {
        this.eventManager.broadcast({ name: 'zoneListModification', content: 'OK'});
        this.isSaving = false;
        this.zone = result;
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
    selector: 'jhi-zone-popup',
    template: ''
})
export class ZonePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zonePopupService: ZonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.zonePopupService
                    .open(ZoneDialogComponent, params['id']);
            } else {
                this.modalRef = this.zonePopupService
                    .open(ZoneDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
