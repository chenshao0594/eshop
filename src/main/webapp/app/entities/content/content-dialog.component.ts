import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Content } from './content.model';
import { ContentPopupService } from './content-popup.service';
import { ContentService } from './content.service';

@Component({
    selector: 'jhi-content-dialog',
    templateUrl: './content-dialog.component.html'
})
export class ContentDialogComponent implements OnInit {

    content: Content;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private contentService: ContentService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['content', 'contentType', 'contentPosition']);
        this.content = new Content();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.content.id !== undefined) {
            this.contentService.update(this.content)
                .subscribe((res: Content) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.contentService.create(this.content)
                .subscribe((res: Content) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Content) {
        this.eventManager.broadcast({ name: 'contentListModification', content: 'OK'});
        this.isSaving = false;
        this.content = result;
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
}

@Component({
    selector: 'jhi-content-popup',
    template: ''
})
export class ContentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentPopupService: ContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.contentPopupService
                    .open(ContentDialogComponent, params['id']);
            } else {
                this.modalRef = this.contentPopupService
                    .open(ContentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
