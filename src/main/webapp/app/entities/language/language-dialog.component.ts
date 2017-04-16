import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Language } from './language.model';
import { LanguagePopupService } from './language-popup.service';
import { LanguageService } from './language.service';

@Component({
    selector: 'jhi-language-dialog',
    templateUrl: './language-dialog.component.html'
})
export class LanguageDialogComponent implements OnInit {

    language: Language;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['language']);
        this.language = new Language();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.language.id !== undefined) {
            this.languageService.update(this.language)
                .subscribe((res: Language) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.languageService.create(this.language)
                .subscribe((res: Language) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Language) {
        this.eventManager.broadcast({ name: 'languageListModification', content: 'OK'});
        this.isSaving = false;
        this.language = result;
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

    trackMerchantStoreById(index: number, item: MerchantStore) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-language-popup',
    template: ''
})
export class LanguagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private languagePopupService: LanguagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.languagePopupService
                    .open(LanguageDialogComponent, params['id']);
            } else {
                this.modalRef = this.languagePopupService
                    .open(LanguageDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
