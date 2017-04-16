import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { FileHistory } from './file-history.model';
import { FileHistoryPopupService } from './file-history-popup.service';
import { FileHistoryService } from './file-history.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-file-history-dialog',
    templateUrl: './file-history-dialog.component.html'
})
export class FileHistoryDialogComponent implements OnInit {

    fileHistory: FileHistory;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
                constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private fileHistoryService: FileHistoryService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['fileHistory']);
        this.fileHistory = new FileHistory();
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
        if (this.fileHistory.id !== undefined) {
            this.fileHistoryService.update(this.fileHistory)
                .subscribe((res: FileHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.fileHistoryService.create(this.fileHistory)
                .subscribe((res: FileHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: FileHistory) {
        this.eventManager.broadcast({ name: 'fileHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.fileHistory = result;
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
}

@Component({
    selector: 'jhi-file-history-popup',
    template: ''
})
export class FileHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fileHistoryPopupService: FileHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fileHistoryPopupService
                    .open(FileHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.fileHistoryPopupService
                    .open(FileHistoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
