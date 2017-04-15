import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FileHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/file-history/file-history-detail.component';
import { FileHistoryService } from '../../../../../../main/webapp/app/entities/file-history/file-history.service';
import { FileHistory } from '../../../../../../main/webapp/app/entities/file-history/file-history.model';

describe('Component Tests', () => {

    describe('FileHistory Management Detail Component', () => {
        let comp: FileHistoryDetailComponent;
        let fixture: ComponentFixture<FileHistoryDetailComponent>;
        let service: FileHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [FileHistoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FileHistoryService,
                    EventManager
                ]
            }).overrideComponent(FileHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FileHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fileHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
