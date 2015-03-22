//
//  AudioController.h
//  Animus
//
//  Created by Bartlomiej Siemieniuk on 21/03/2015.
//  Copyright (c) 2015 TeamGoat. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "Streamer.h"
#import <AudioToolbox/AudioQueue.h>
#import <AudioToolbox/AudioFile.h>

#define NUM_BUFFERS         3
#define SECONDS_TO_RECORD   10

typedef struct
{
    AudioStreamBasicDescription  dataFormat;
    AudioQueueRef                queue;
    AudioQueueBufferRef          buffers[NUM_BUFFERS];
    AudioFileID                  audioFile;
    SInt64                       currentPacket;
    bool                         recording;
} RecordState;

@interface AudioController : NSObject {
    RecordState recordState;
    CFURLRef fileURL;
}

@property Streamer * streamer;

+ (void)handleData:(NSData *)data;
- (void)initRecording;
- (id) initWith: (Streamer *)streamerObject;

@end
